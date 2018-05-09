package ludwigsamuel.custom_speedtest.util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class SpeedtestParameters {


    public static final String DEFAULT_FILE_URL = "http://speedtest.tele2.net/10GB.zip";
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final int DEFAULT_DURATION = 15000;
    public static final int DEFAULT_POLL_INTERVAL = 100;
    public static final int DEFAULT_ADAPT_INTERVAL = 1000;
    public static final int DEFAULT_ADAPT_THRESHHOLD = 1000;
    public static final int DEFAULT_SINGLE_THREAD_COUNT = 1;
    public static final int DEFAULT_MULTI_THREAD_COUNT = 5;
    public static final int DEFAULT_MIN_THREAD_COUNT = 1;
    public static final int DEFAULT_MAX_THREAD_COUNT = 10;
    public static final int DEFAULT_WIFI_INTERVAL = 500;
    public static final int DEFAULT_ADAPT_SAMPLE_COUNT = 10;

    public enum AverageMode {
        STANDARD, SPEEDTEST_DOT_NET
    }

    public enum ThreadingMode {
        SINGLE, MULTI, ADAPTIVE
    }

    public enum State {
        TESTING, IDLE
    }

    private State state;
    private URL fileURL;
    private int bufferSize;
    private AverageMode averageMode;
    private ThreadingMode threadingMode;
    private int threadCount;
    private int minThreadCount;
    private int maxThreadCount;
    private int pollInterval;
    private int duration;
    private int adaptInterval;
    private int adaptThreshold;
    private int adaptSampleCount;
    private int wifiInterval;
    private SampleContainer<Double> bandwidthSampleContainer;
    private SampleContainer<Integer> wifiSampleContainer;
    private SampleContainer<Integer> threadSampleContainer;

    public SpeedtestParameters() {
        state = State.IDLE;
        bandwidthSampleContainer = new SampleContainer<>();
        wifiSampleContainer = new SampleContainer<>();
        threadSampleContainer = new SampleContainer<>();
        try {
            fileURL = new URL(DEFAULT_FILE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        bufferSize = DEFAULT_BUFFER_SIZE;
        averageMode = AverageMode.STANDARD;
        threadingMode = ThreadingMode.SINGLE;
        threadCount = DEFAULT_SINGLE_THREAD_COUNT;
        minThreadCount = DEFAULT_MIN_THREAD_COUNT;
        maxThreadCount = DEFAULT_MAX_THREAD_COUNT;
        pollInterval = DEFAULT_POLL_INTERVAL;
        duration = DEFAULT_DURATION;
        adaptInterval = DEFAULT_ADAPT_INTERVAL;
        adaptThreshold = DEFAULT_ADAPT_THRESHHOLD;
        adaptSampleCount = DEFAULT_ADAPT_SAMPLE_COUNT;
        wifiInterval = DEFAULT_WIFI_INTERVAL;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public URL getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        try {
            this.fileURL = new URL(fileURL);
        } catch (MalformedURLException muE) {
            Log.d("setFileURL", "exception caught: " + muE.getMessage());
        }
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public AverageMode getAverageMode() {
        return averageMode;
    }

    public void setAverageMode(AverageMode averageMode) {
        this.averageMode = averageMode;
    }

    public ThreadingMode getThreadingMode() {
        return threadingMode;
    }

    public void setThreadingMode(ThreadingMode threadingMode) {
        this.threadingMode = threadingMode;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getMinThreadCount() {
        return minThreadCount;
    }

    public void setMinThreadCount(int minThreadCount) {
        this.minThreadCount = minThreadCount;
    }

    public int getMaxThreadCount() {
        return maxThreadCount;
    }

    public void setMaxThreadCount(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    public int getPollInterval() {
        return pollInterval;
    }

    public void setPollInterval(int pollInterval) {
        this.pollInterval = pollInterval;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public SampleContainer<Double> getBandwidthSampleContainer() {
        return bandwidthSampleContainer;
    }

    public SampleContainer<Integer> getWifiSampleContainer() {
        return wifiSampleContainer;
    }

    public SampleContainer<Integer> getThreadSampleContainer() {
        return threadSampleContainer;
    }

    public int getSampleCount() {
        return duration / pollInterval;
    }

    public int getAdaptInterval() {
        return adaptInterval;
    }

    public void setAdaptInterval(int adaptInterval) {
        this.adaptInterval = adaptInterval;
    }

    public int getAdaptThreshold() {
        return adaptThreshold;
    }

    public void setAdaptThreshold(int adaptThreshold) {
        this.adaptThreshold = adaptThreshold;
    }

    public int getAdaptSampleCount() {
        return adaptSampleCount;
    }

    public void setAdaptSampleCount(int adaptSampleCount) {
        this.adaptSampleCount = adaptSampleCount;
    }

    public int getWifiInterval() {
        return wifiInterval;
    }

    public void setWifiInterval(int wifiInterval) {
        this.wifiInterval = wifiInterval;
    }

    public boolean isAdaptive() {
        return threadingMode == ThreadingMode.ADAPTIVE;
    }
}

