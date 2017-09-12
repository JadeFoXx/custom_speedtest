package ludwigsamuel.custom_speedtest.ui;

import android.view.View;

/**
 * Created by Ludwig Samuel on 12-Nov-16.
 */

public class Bar {
    private View view;
    private double sample;

    public Bar(View view, double sample) {
        this.view = view;
        this.sample = sample;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public double getSample() {
        return sample;
    }

    public void setSample(double sample) {
        this.sample = sample;
    }
}
