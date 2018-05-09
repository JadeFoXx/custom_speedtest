package ludwigsamuel.custom_speedtest.ui;

/**
 * Created by Ludwig Samuel on 12-Nov-16.
 */

public class Bar {

    private double sample;

    public Bar(double sample) {
        this.sample = sample;
    }

    public float calculateWidth(int width, int count) {
        return ((float)width)/count;
    }

    public float calculateHeight(double max, int height) {
        float percentage = (((float)sample / (float)max) * 100f);
        return ((float)height-(((float)height / 100f) * percentage));

    }
}
