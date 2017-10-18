package ludwigsamuel.custom_speedtest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Ludwig Samuel on 10/18/2017.
 */

public class ThreadController extends TimerTask {

    private SpeedtestParameters speedtestParameters;
    private ArrayList<Thread> threads;
    private boolean firstIteration;
    private double probeOne;
    private double probeTwo;
    private Runnable r;

    public ThreadController(SpeedtestParameters speedtestParameters, Runnable r) {
        this.speedtestParameters = speedtestParameters;
        threads = new ArrayList<>();
        this.r = r;
        firstIteration = true;
    }

    @Override
    public void run() {
        probeTwo = Calc.average(getProbeSamples(speedtestParameters.getBandwidthSampleContainer()));
        if(!firstIteration) {
            if(Math.abs(probeOne-probeTwo) > speedtestParameters.getAdaptThreshold() && probeTwo > probeOne && threads.size() < speedtestParameters.getMaxThreadCount()) {
                addThread(r);
            }
            if(Math.abs(probeOne-probeTwo) > speedtestParameters.getAdaptThreshold() && probeTwo < probeOne && threads.size() > speedtestParameters.getMinThreadCount()) {
                Thread thread = threads.get(threads.size()-1);
                removeThread(thread);
            }
        }
        probeOne = Calc.average(getProbeSamples(speedtestParameters.getBandwidthSampleContainer()));
        firstIteration = false;
    }

    public Thread addThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.MAX_PRIORITY);
        threads.add(thread);
        thread.start();
        speedtestParameters.getThreadSampleContainer().add(threads.size());
        return thread;
    }

    public void removeThread(Thread thread) {
        threads.remove(thread);
        thread.interrupt();
        speedtestParameters.getThreadSampleContainer().add(threads.size());
    }

    public void removeAll() {
        for(Thread t : threads) {
            t.interrupt();
        }
        threads.clear();
    }


    private List<Double> getProbeSamples(ArrayList<Double> samples) {
        if(samples.size() >= 10) {
            return samples.subList(samples.size()-10, samples.size()-1);
        }
        return samples;
    }
}
