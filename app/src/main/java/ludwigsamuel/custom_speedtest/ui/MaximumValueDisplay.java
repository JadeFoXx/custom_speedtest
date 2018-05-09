package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by secur on 7/17/2017.
 */

public class MaximumValueDisplay implements Pushable<ArrayList<Double>> {


    private double maximum = 0;
    private TextView textView;
    private Activity parentActivity;
    private Double multiplier;


    public MaximumValueDisplay(Activity activity, TextView tv) {
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
    public void push(final ArrayList<Double> values) {
        Double value = values.get(values.size() - 1);
        if (value >= maximum) {
            maximum = value;
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
        ArrayList<Double> list = new ArrayList<>();
        list.add(0d);
        push(list);
    }
}
