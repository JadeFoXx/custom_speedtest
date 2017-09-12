package ludwigsamuel.custom_speedtest.util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class SpeedtestParameters {

    public static final String DEFAULT_FILE_URL = "http://download.thinkbroadband.com/1GB.zip";
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final int DEFAULT_DURATION = 15000;
    public static final int DEFAULT_POLLRATE = 100;
    public static final int DEFAULT_PROBEINTERVAL = 1000;
    public static final int AVERAGE_STANDARD = 0;
    public static final int AVERAGE_SPEEDTEST_DOT_NET = 1;

    private URL fileURL;
    private int buffersize;
    private int threadcount;
    private int minThreadcount;
    private int maxThreacount;
    private int pollInterval;
    private int sampleCount;
    private int duration;
    private boolean isAdaptive;
    private int adaptionInterval;
    private int averageType;
    private SampleContainer sampleContainer;

    public SpeedtestParameters() {
        sampleContainer = new SampleContainer();
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

    public int getBuffersize() {
        return buffersize;
    }

    public void setBuffersize(int buffersize) {
        this.buffersize = buffersize;
    }

    public int getThreadcount() {
        return threadcount;
    }

    public void setThreadcount(int threadcount) {
        this.threadcount = threadcount;
    }

    public int getMinThreadcount() {
        return minThreadcount;
    }

    public void setMinThreadcount(int minThreadcount) {
        this.minThreadcount = minThreadcount;
    }

    public int getMaxThreacount() {
        return maxThreacount;
    }

    public void setMaxThreacount(int maxThreacount) {
        this.maxThreacount = maxThreacount;
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

    public boolean isAdaptive() {
        return isAdaptive;
    }

    public void setAdaptive(boolean adaptive) {
        isAdaptive = adaptive;
    }

    public SampleContainer getSampleContainer() {
        return sampleContainer;
    }

    public int getSampleCount() { return duration / pollInterval; }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public int getAdaptionInterval() {
        return adaptionInterval;
    }

    public void setAdaptionInterval(int adaptionInterval) {
        this.adaptionInterval = adaptionInterval;
    }

    public int getAverageType() {
        return averageType;
    }

    public void setAverageType(int averageType) {
        this.averageType = averageType;
    }

    public static SpeedtestParameters getDefaultSpeedtestParameters() {
        SpeedtestParameters speedtestParameters = new SpeedtestParameters();
        speedtestParameters.setFileURL(DEFAULT_FILE_URL);
        speedtestParameters.setAdaptive(false);
        speedtestParameters.setThreadcount(1);
        speedtestParameters.setBuffersize(DEFAULT_BUFFER_SIZE);
        speedtestParameters.setDuration(DEFAULT_DURATION);
        speedtestParameters.setPollInterval(DEFAULT_POLLRATE);
        speedtestParameters.setAdaptionInterval(DEFAULT_PROBEINTERVAL);
        return speedtestParameters;
    }

}

