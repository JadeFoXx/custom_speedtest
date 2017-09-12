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
    private int pingcount;
    private double ping;

    public PingtestParameters() {
        sampleContainer = new SampleContainer();
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
        return pingcount;
    }

    public void setPingcount(int pingcount) {
        this.pingcount = pingcount;
    }

    public static PingtestParameters getDefaultPingtestParameters() {
        PingtestParameters pingtestParameters = new PingtestParameters();
        pingtestParameters.setPingcount(DEFAULT_PING_COUNT);
        pingtestParameters.setPingURL(DEFAULT_PING_URL);
        return pingtestParameters;
    }
}
