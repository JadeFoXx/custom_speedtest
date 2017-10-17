package ludwigsamuel.custom_speedtest.ui;

/**
 * Created by Ludwig Samuel on 12-Nov-16.
 */

public class Bar {

    private double sample;
    private float x;
    private float y;
    private float width;
    private float height;

    public Bar(double sample, float x, float width) {
        this.sample = sample;
        this.x = x;
        this.width = width;
    }

    public double getSample() {
        return sample;
    }

    public void setSample(double sample) {
        this.sample = sample;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public int calculateHeight(double max, int height) {
        int percentage = (int) ((sample / max) * 100);
        return (int) (height-((height / 100d) * percentage));
    }
}
