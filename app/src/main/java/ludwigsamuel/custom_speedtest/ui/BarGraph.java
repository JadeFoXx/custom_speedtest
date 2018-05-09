package ludwigsamuel.custom_speedtest.ui;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
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
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        handler = new Handler(context.getMainLooper());
        bars = new ArrayList<>();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        lastX = 0;
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        paint.setColor(getResources().getColor(R.color.colorWhite));
        paint.setStrokeWidth(0);
        for (Bar bar : bars) {
            canvas.drawRect(lastX, bar.calculateHeight(max, getHeight()), lastX + bar.calculateWidth(getWidth(), bars.size()), getHeight(), paint);
            lastX += bar.calculateWidth(getWidth(), bars.size());
        }
    }

    public void addBar(final double sample) {

        if (max == null || max < sample) {
            max = sample;
        }
        Bar bar = new Bar(sample);
        bars.add(bar);
        invalidate();
    }

    public void reset() {
        max = null;
        bars.clear();
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
                addBar(value.get(value.size() - 1) * getMultiplier());
            }
        };
        handler.post(r);
    }

}
