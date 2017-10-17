package ludwigsamuel.custom_speedtest.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class Calc {



    public static double average(List<Double> samples) {
        double sampleSum = 0;
        for(int i=0; i < samples.size(); i++) {
            sampleSum = sampleSum + samples.get(i);
        }
        return (sampleSum / samples.size());
    }

    public static double speedtestDotNetAverage(List<Double> samples) {
        List<Double> workingSamples = samples;
        if(workingSamples.size() > 4) {
            int cutCount = workingSamples.size() / 4;
            for(int i = 0; i <= cutCount; i++) {
                workingSamples.remove(0);
            }
            for(int i = 0; i < 2; i++) {
                workingSamples.remove(workingSamples.size()-1);
            }
        }
        return average(workingSamples);
    }

}
