package ludwigsamuel.custom_speedtest.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.TimerTask;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 10/13/2017.
 */

public class WifiStrengthSampler{

    private SampleContainer sampleContainer;
    private int maxSamples;
    private BroadcastReceiver broadcastReceiver;

    public WifiStrengthSampler(final SampleContainer sampleContainer, final int maxSamples) {
        this.sampleContainer = sampleContainer;
        this.maxSamples = maxSamples;
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(sampleContainer.getAllSamples().size() >= maxSamples) {
                    cancel();
                }
                sampleContainer.addSample(intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0));
            }
        };

        App.getAppContext().registerReceiver(broadcastReceiver, filter);
    }

    private void cancel() {
        App.getAppContext().unregisterReceiver(broadcastReceiver);
        this.cancel();
    }
}
