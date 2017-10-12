package ludwigsamuel.custom_speedtest.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ludwigsamuel.custom_speedtest.ui.Pushable;


/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class SampleContainer<T> {

    private ArrayList<T> samples;
    private List<Pushable> pushables;

    public SampleContainer() {
        samples = new ArrayList<>();
        pushables = new ArrayList<>();
    }

    public void addSample(T sample) {
        if (sample != null) {
            samples.add(sample);
            Log.d("addSample", "sample added: " + sample);
        }
        for (Pushable p : pushables) {
            p.push(samples);
        }
    }

    public void registerDataContainer(Pushable container) {
        pushables.add(container);
    }

    public ArrayList<T> getAllSamples() {
        return samples;
    }
}
