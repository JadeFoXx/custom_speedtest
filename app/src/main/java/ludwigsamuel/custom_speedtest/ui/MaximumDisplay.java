package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by secur on 7/17/2017.
 */

public class MaximumDisplay implements Pushable<Double> {


    private double maximum = 0;
    private TextView textView;
    private Activity parentActivity;
    private Double multiplier;


    public MaximumDisplay(Activity activity, TextView tv) {
        parentActivity = activity;
        textView = tv;
    }

    @Override
    public void setMultiplier(Double mul) {
        multiplier = mul;
    }

    @Override
    public Double getMultiplier() {
        if (multiplier != null) {
            return multiplier;
        }
        return 1d;
    }

    @Override
    public void push(final Double value) {
        if (value >= maximum) {
            maximum = value;jjj
            display(value);
        }
    }

    private void display(final Double value) {
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(String.format("Maximum: %.2f Mbit/s", value * getMultiplier()));
            }
        });
    }

    public void reset() {
        maximum = 0d;
        push(0d);
    }
}
