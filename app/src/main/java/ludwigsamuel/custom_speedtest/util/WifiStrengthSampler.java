package ludwigsamuel.custom_speedtest.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.util.TimerTask;

/**
 * Created by Ludwig Samuel on 10/13/2017.
 */

public class WifiStrengthSampler extends TimerTask{

    private SpeedtestParameters speedtestParameters;

    public WifiStrengthSampler(final SpeedtestParameters speedtestParameters) {
        this.speedtestParameters = speedtestParameters;
    }

    @Override
    public void run() {
        if(speedtestParameters.getState() != SpeedtestParameters.State.TESTING) {
            cancel();
        }
        WifiManager wifiManager = (WifiManager)App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        speedtestParameters.getWifiSampleContainer().add(wifiInfo.getRssi());
    }
}
