package ludwigsamuel.custom_speedtest.ui;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ludwigsamuel.custom_speedtest.R;

/**
 * Created by Ludwig Samuel on 12-Nov-16.
 */

public class BarGraph extends View implements Pushable<ArrayList<Double>> {


    private Paint paint;
    private List<Bar> bars;
    private Double max;
    private Double multiplier;
    private float lastX;
    private Handler handler;

    public BarGraph(Context context) {
        this(context, null);
    }

    public BarGraph(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BarGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handler = new Handler(context.getMainLooper());
        bars = new ArrayList<>();
        paint = new Paint();
        lastX = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.CLEAR);
        paint.setColor(getResources().getColor(R.color.colorWhite));
        paint.setStrokeWidth(0);
        for(Bar b : bars) {
            canvas.drawRect(b.getX(), b.getY(), b.getX()+b.getWidth(), b.getY()+b.getHeight(), paint);
        }
    }

    public void addBar(final double sample) {

        if(max == null || max < sample) {
            max = sample;
        }
        Bar bar = new Bar(sample, lastX, getHeight()-getRelativeBarHeight(getPercentageOfMax(sample)), getDefaultBarWidth(), getHeight());
        bars.add(bar);

        invalidate();
        lastX += getDefaultBarWidth();
    }

    private int getPercentageOfMax(double sample) {
        int i = (int) ((sample / max) * 100);
        return i;
    }

    private Integer getRelativeBarWidth(int sampleCount) {
        return (getWidth() / sampleCount);
    }

    private Integer getDefaultBarWidth() {
        return 8;
    }

    private Integer getRelativeBarHeight(int percentage) {
        int i = (int) ((getHeight() / 100d) * percentage);
        return i;
    }

    public void reset() {
        max = null;
        bars.clear();
        lastX = 0;
        handler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
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
    public void push(final ArrayList<Double> value) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                addBar(value.get(value.size()-1) * getMultiplier());
            }
        };
        handler.post(r);
    }

}
