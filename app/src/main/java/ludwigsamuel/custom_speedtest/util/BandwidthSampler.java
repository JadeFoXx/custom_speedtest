package ludwigsamuel.custom_speedtest.util;

import android.net.TrafficStats;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class BandwidthSampler {
    private Thread samplingThread;
    private boolean isSampling;

    public void startSampling(final SampleContainer samples, final int pollInterval) {
        isSampling = true;
        samplingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Nox nox = new Nox();
                while (isSampling) {
                    long startTime = System.currentTimeMillis();
                    long startBytes = TrafficStats.getTotalRxBytes();
                    nox.sleep(pollInterval);
                    long endTime = System.currentTimeMillis();
                    long endBytes = TrafficStats.getTotalRxBytes();
                    double sample = ((endBytes - startBytes) * 8) / (endTime - startTime);
                    if(isSampling) {
                        samples.addSample(sample);
                    }
                }
            }
        });
        samplingThread.start();
    }

    public void stopSampling() {isSampling = false;}

    public boolean isSampling() {return isSampling;}
}
