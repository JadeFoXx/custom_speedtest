package ludwigsamuel.custom_speedtest.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ludwigsamuel.custom_speedtest.ui.Pushable;


/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class SampleContainer<T> extends ArrayList<T> {

    private List<Pushable> pushables;

    public SampleContainer() {
        pushables = new ArrayList<>();
    }

    @Override
    public boolean add(T sample) {
        boolean result = false;
        if (sample != null) {
            result = super.add(sample);
            Log.d("addSample", "sample added: " + sample);
        }
        for (Pushable p : pushables) {
            p.push(this);
        }
        return result;
    }
    /*
    public void addSample(T sample) {
        if (sample != null) {
            add(sample);
            Log.d("addSample", "sample added: " + sample);
        }
        for (Pushable p : pushables) {
            p.push(this);
        }
    }
    */

    public void registerDataContainer(Pushable container) {
        pushables.add(container);
    }

}
