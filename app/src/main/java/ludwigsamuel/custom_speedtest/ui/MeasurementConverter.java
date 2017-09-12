package ludwigsamuel.custom_speedtest.ui;

import android.content.res.Resources;

/**
 * Created by Ludwig Samuel on 12-Nov-16.
 */

public final class MeasurementConverter {
    private MeasurementConverter() {

    }
    public static float dp2px(Resources resources, float dp) {
        final float dens = resources.getDisplayMetrics().density;
        return dp *  dens + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final  float dens = resources.getDisplayMetrics().scaledDensity;
        return sp * dens;
    }
}
