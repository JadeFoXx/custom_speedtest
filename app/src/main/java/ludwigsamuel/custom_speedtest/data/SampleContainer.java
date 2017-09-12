package ludwigsamuel.custom_speedtest.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ludwigsamuel.custom_speedtest.ui.ArcProgressBar;
import ludwigsamuel.custom_speedtest.ui.Pushable;
import ludwigsamuel.custom_speedtest.util.Calc;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class SampleContainer {

    private ArrayList<Double> samples;
    private List<Pushable> pushables;

    public SampleContainer() {
        samples = new ArrayList<>();
        pushables = new ArrayList<>();
    }

    public void addSample(Double sample) {
        if (sample != null) {
            samples.add(sample);
            Log.d("addSample", "sample added: " + sample);
        }
        for (Pushable p : pushables) {
            if (p instanceof ArcProgressBar) {
                p.push(Calc.average(samples));
            } else {
                p.push(sample);
            }
        }
    }

    public void registerDataContainer(Pushable container) {
        pushables.add(container);
    }

    public ArrayList<Double> getAllSamples() {
        return samples;
    }

    public Double getSampleAtPosition(int index) {
        if (index < samples.size()) {
            return samples.get(index);
        }
        return null;
    }
}
