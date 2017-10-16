package ludwigsamuel.custom_speedtest.util;

import android.net.TrafficStats;

import java.util.TimerTask;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class BandwidthSampler extends TimerTask {


    private SampleContainer sampleContainer;
    private long startTime = 0;
    private long startBytes = 0;
    private long endTime = 0;
    private long endBytes = 0;
    private boolean firstRepetition = true;
    private int maxSamples;

    public BandwidthSampler(SampleContainer sampleContainer, int maxSamples) {
        this.sampleContainer = sampleContainer;
        this.maxSamples = maxSamples;
    }

    @Override
    public void run() {
        if(sampleContainer.getAllSamples().size() >= maxSamples) {
            this.cancel();
        }
        endTime = System.currentTimeMillis();
        endBytes = TrafficStats.getTotalRxBytes();
        if(!firstRepetition) {
            double sample = ((endBytes - startBytes) * 8) / (endTime - startTime);
            sampleContainer.addSample(sample);
        }
        startTime = System.currentTimeMillis();
        startBytes = TrafficStats.getTotalRxBytes();
        firstRepetition = false;
    }
}
