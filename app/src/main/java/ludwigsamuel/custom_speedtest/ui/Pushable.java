package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by secur on 5/13/2017.
 */

public interface Pushable<V> {

    public void setMultiplier(Double mul);

    public Double getMultiplier();

    public void push(V value);

    public void reset();
}
