package ludwigsamuel.custom_speedtest.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class Test {
    private static final String PINGPATH = "/system/bin/ping -c ";

    public void ping(PingtestParameters pingtestParameters) {
        String pingResult;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process pingProcess = runtime.exec(PINGPATH + pingtestParameters.getPingcount() + ' ' + pingtestParameters.getPingURL());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            pingResult = stringBuilder.toString();
            bufferedReader.close();
            pingProcess.destroy();
            List<Double> pings = parsePingResultToDoubles(pingResult);
            for (Double d : pings) {
                pingtestParameters.getSampleContainer().add(d);
            }
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    private List<Double> parsePingResultToDoubles(String result) {
        List<Double> doubles = new ArrayList<>();
        Pattern pingPattern = Pattern.compile("time=(\\d+\\.?\\d?)");
        Matcher pingMatcher = pingPattern.matcher(result);

        while (pingMatcher.find()) {
            doubles.add(Double.parseDouble(pingMatcher.group(1)));
        }
        return doubles;
    }

    public void speed(final SpeedtestParameters speedtestParameters) {
        speedtestParameters.setState(SpeedtestParameters.State.TESTING);

        WifiStrengthSampler wifiStrengthSampler = new WifiStrengthSampler(speedtestParameters);
        Timer wifiTimer = new Timer();
        BandwidthSampler bandwidthSampler = new BandwidthSampler(speedtestParameters);
        Timer bandwidthTimer = new Timer();

        wifiTimer.schedule(wifiStrengthSampler, 0, speedtestParameters.getWifiInterval());
        bandwidthTimer.schedule(bandwidthSampler, 0, speedtestParameters.getPollInterval());

        ThreadController threadController = new ThreadController(speedtestParameters);
        if (!speedtestParameters.isAdaptive()) {
            for (int i = 0; i < speedtestParameters.getThreadCount(); i++) {
                threadController.addThread();
            }
        } else {
            threadController.addThread();
            threadController.adapt();
        }
    }
}
