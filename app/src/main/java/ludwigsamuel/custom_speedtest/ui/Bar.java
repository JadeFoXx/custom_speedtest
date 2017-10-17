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

    public Bar(double sample, float x, float y, float width, float height) {
        this.sample = sample;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
