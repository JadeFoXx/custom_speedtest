package ludwigsamuel.custom_speedtest.util;

import ludwigsamuel.custom_speedtest.data.SampleContainer;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class PingtestParameters {

    public static final String DEFAULT_PING_URL = "8.8.8.8";
    public static final int DEFAULT_PING_COUNT = 10;

    private SampleContainer sampleContainer;
    private String pingURL;
    private int pingCount;

    public PingtestParameters() {
        sampleContainer = new SampleContainer();
        this.pingURL = DEFAULT_PING_URL;
        this.pingCount = DEFAULT_PING_COUNT;
    }

    public SampleContainer getSampleContainer() {
        return sampleContainer;
    }

    public String getPingURL() {
        return pingURL;
    }

    public void setPingURL(String pingURL) {
        this.pingURL = pingURL;
    }

    public int getPingcount() {
        return pingCount;
    }

    public void setPingCount(int pingcount) {
        this.pingCount = pingcount;
    }
}
