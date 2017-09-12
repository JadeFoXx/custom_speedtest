package ludwigsamuel.custom_speedtest.util;

import android.util.Log;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class Nox {
    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            Log.d("nox", e.getMessage());
        }
    }
}
