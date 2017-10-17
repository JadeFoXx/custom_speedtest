package ludwigsamuel.custom_speedtest.util;

import android.net.TrafficStats;

import java.util.TimerTask;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class BandwidthSampler extends TimerTask {


    private SpeedtestParameters speedtestParameters;
    private long startTime = 0;
    private long startBytes = 0;
    private long endTime = 0;
    private long endBytes = 0;
    private boolean firstRepetition = true;

    public BandwidthSampler(SpeedtestParameters speedtestParameters) {
        this.speedtestParameters = speedtestParameters;
    }

    @Override
    public void run() {
        if (speedtestParameters.getBandwidthSampleContainer().size() >= speedtestParameters.getSampleCount()) {
            speedtestParameters.setState(SpeedtestParameters.State.IDLE);
            cancel();
        }
        endTime = System.currentTimeMillis();
        endBytes = TrafficStats.getTotalRxBytes();
        if (!firstRepetition) {
            double sample = ((endBytes - startBytes) * 8) / (endTime - startTime);
            speedtestParameters.getBandwidthSampleContainer().add(sample);
        }
        startTime = System.currentTimeMillis();
        startBytes = TrafficStats.getTotalRxBytes();
        firstRepetition = false;
    }
}
