package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ludwig Samuel on 10/13/2017.
 */

public class LastValueDisplay implements Pushable<ArrayList<Integer>> {

    private Activity activity;
    private TextView textView;
    private Double multiplier;

    public LastValueDisplay(Activity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
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
    public void push(ArrayList<Integer> value) {
        display(value.get(value.size()-1));
    }

    private void display(final Integer value) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(Integer.toString(value));
            }
        });
    }

    @Override
    public void reset() {
        display(0);
    }
}
