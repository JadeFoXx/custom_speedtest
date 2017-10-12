package ludwigsamuel.custom_speedtest.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class Test {
    private ArrayList<Thread> activeThreads;
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
            for(Double d : pings) {
                pingtestParameters.getSampleContainer().addSample(d);
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

    private boolean generateTraffic(URL url, int buffersize, SampleContainer samples, int sampleMaxCount) {
        URLConnection urlConnection = prepareConnection(url);
        if(urlConnection != null) {
            try {
                urlConnection.connect();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                byte[] buffer = new byte[buffersize];
                try {
                    while (inputStream.read(buffer) != -1 && samples.getAllSamples().size() < sampleMaxCount && !Thread.currentThread().isInterrupted()) {
                        //create traffic
                    }
                } finally {
                    inputStream.close();
                    Log.d("generateTraffic", "generated traffic");
                    return true;
                }
            } catch (IOException ioE) {
                ioE.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void speed(final SpeedtestParameters speedtestParameters) {

        activeThreads = new ArrayList<>();
        Nox nox = new Nox();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    boolean result = generateTraffic(speedtestParameters.getFileURL(), speedtestParameters.getBuffersize(), speedtestParameters.getBandwidthSampleContainer(), speedtestParameters.getSampleCount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        BandwidthSampler bandwidthSampler = new BandwidthSampler();
        bandwidthSampler.startSampling(speedtestParameters);
        if(!speedtestParameters.isAdaptive()) {
            for (int i = 0; i < speedtestParameters.getThreadcount(); i++) {
                activeThreads.add(startThread(r));
                Log.d("Downloadtest: ", "Thread started");
            }
            while (speedtestParameters.getBandwidthSampleContainer().getAllSamples().size() < speedtestParameters.getSampleCount()) {
                //wait
            }
        } else {
            for(int i = 0; i < speedtestParameters.getMinThreadcount(); i++) {
                activeThreads.add(startThread(r));
            }
            nox.sleep(speedtestParameters.getAdaptionInterval());
            while (speedtestParameters.getBandwidthSampleContainer().getAllSamples().size() < speedtestParameters.getSampleCount()) {
                double probeOne = new Calc().average(speedtestParameters.getBandwidthSampleContainer().getAllSamples());
                nox.sleep(speedtestParameters.getAdaptionInterval());
                double probeTwo = new Calc().average(speedtestParameters.getBandwidthSampleContainer().getAllSamples());
                if(probeOne < probeTwo && activeThreads.size() < speedtestParameters.getMaxThreacount()) {
                    activeThreads.add(startThread(r));
                } else if(probeOne > probeTwo && activeThreads.size() > speedtestParameters.getMinThreadcount()) {
                    Thread thread = activeThreads.get(activeThreads.size()-1);
                    activeThreads.remove(thread);
                    thread.interrupt();
                }
            }
        }
        bandwidthSampler.stopSampling();
    }

    private Thread startThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        return thread;
    }

    private URLConnection prepareConnection(URL url) {
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            return urlConnection;
        } catch (IOException ioE) {
            Log.d("prepareConnection", "Exception caught: " + ioE.getMessage());
            return null;
        }
    }

}
