package ludwigsamuel.custom_speedtest.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ludwig Samuel on 10/18/2017.
 */

public class ThreadController extends ArrayList<Thread> {

    private SpeedtestParameters speedtestParameters;

    public ThreadController(SpeedtestParameters speedtestParameters) {
        this.speedtestParameters = speedtestParameters;
    }

    private class AdaptThreadTask extends TimerTask {

        private boolean firstIteration = true;
        private double probeOne;
        private double probeTwo;

        @Override
        public void run() {
            if(speedtestParameters.getState() != SpeedtestParameters.State.TESTING) {
                removeAll();
                cancel();
            } else {
                probeTwo = Calc.average(getProbeSamples(speedtestParameters.getBandwidthSampleContainer()));
                if(!firstIteration) {
                    if(Math.abs(probeTwo-probeOne) > speedtestParameters.getAdaptThreshold()) {
                        if(probeTwo > probeOne && size() < speedtestParameters.getMaxThreadCount()) {
                            addThread();
                        } else if(probeTwo < probeOne && size() > speedtestParameters.getMinThreadCount()) {
                            removeThread();
                        }
                    }
                }
                probeOne = Calc.average(getProbeSamples(speedtestParameters.getBandwidthSampleContainer()));
                firstIteration = false;
            }
        }
    }

    public void adapt() {
        Timer timer = new Timer();
        timer.schedule(new AdaptThreadTask(), 0, speedtestParameters.getAdaptInterval());
    }

    public void addThread() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                boolean b = generateTraffic(speedtestParameters);
            }
        };
        Thread thread = new Thread(r);
        thread.setPriority(Thread.MAX_PRIORITY);
        add(thread);
        thread.start();
        speedtestParameters.getThreadSampleContainer().add(size());
    }

    public void removeThread() {
        Thread thread = get(size()-1);
        remove(thread);
        thread.interrupt();
        speedtestParameters.getThreadSampleContainer().add(size());
    }

    public void removeAll() {
        for(Thread t : this) {
            t.interrupt();
        }
        clear();
    }

    private List<Double> getProbeSamples(ArrayList<Double> samples) {
        if(samples.size() >= speedtestParameters.getAdaptSampleCount()) {
            return samples.subList(samples.size()-speedtestParameters.getAdaptSampleCount(), samples.size()-1);
        }
        return samples;
    }

    private boolean generateTraffic(SpeedtestParameters speedtestParameters) {
        URLConnection urlConnection = prepareConnection(speedtestParameters.getFileURL());
        if (urlConnection != null) {
            try {
                urlConnection.connect();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                byte[] buffer = new byte[speedtestParameters.getBufferSize()];
                try {
                    while (inputStream.read(buffer) != -1 && speedtestParameters.getState() == SpeedtestParameters.State.TESTING && !Thread.currentThread().isInterrupted()) {
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
