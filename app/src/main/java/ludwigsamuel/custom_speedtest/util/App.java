package ludwigsamuel.custom_speedtest.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}