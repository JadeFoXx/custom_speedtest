package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ludwigsamuel.custom_speedtest.R;
import ludwigsamuel.custom_speedtest.util.App;

/**
 * Created by Ludwig Samuel on 12-Nov-16.
 */

public class BarGraph implements Pushable<Double> {

    private int sampleCount;
    private LinearLayout rootLayout;
    private List<Bar> bars;
    private Activity activity;
    private Double max;
    private Double multiplier;

    public BarGraph(Activity activity, LinearLayout parentLayout) {
        this.activity = activity;
        this.rootLayout = parentLayout;
        this.bars = new ArrayList<>();
        parentLayout.removeAllViews();
    }

    public void setSampleCount(int count) {
        sampleCount = count;
    }

    public int getRootHeight() {
        return rootLayout.getHeight();
    }

    public int getRootWidth() {
        return rootLayout.getWidth();
    }

    public void addBar(final double sample) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (max == null) {
                    max = sample;
                }
                if (max < sample) {
                    max = sample;
                    redraw();
                }
                LinearLayout view = new LinearLayout(activity);
                Bar bar = new Bar(view, sample);
                bars.add(bar);
                draw(bar, false);
            }
        });
    }

    private void draw(Bar bar, boolean redraw) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getDefaultBarWidth(), getRelativeBarHeight(getPercentageOfMax(bar.getSample())));
        layoutParams.gravity = Gravity.BOTTOM;
        bar.getView().setLayoutParams(layoutParams);
        if (!redraw) {
            bar.getView().setBackgroundColor(App.getAppContext().getResources().getColor(R.color.colorWhite));
            rootLayout.addView(bar.getView());
        }
        rootLayout.invalidate();
        bar.getView().requestLayout();
    }

    private void redraw() {
        for (int i = 0; i < bars.size(); i++) {
            draw(bars.get(i), true);
        }
    }

    private int getPercentageOfMax(double sample) {
        int i = (int) ((sample / max) * 100);
        return i;
    }

    private Integer getRelativeBarWidth(int sampleCount) {
        return (getRootWidth() / sampleCount);
    }

    private Integer getDefaultBarWidth() {
        return 8;
    }

    private Integer getRelativeBarHeight(int percentage) {
        int i = (int) ((getRootHeight() / 100d) * percentage);
        return i;
    }

    public void reset() {
        max = null;
        bars.clear();
        rootLayout.removeAllViews();
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
    public void push(Double value) {
        addBar(value * getMultiplier());
    }

}
