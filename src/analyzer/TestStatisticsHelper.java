package analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TestStatisticsHelper {

    private final Map<Solver, Map<String, Float>> data;

    public TestStatisticsHelper() {
        this(new Solver[]{
            Solver.NEAREST_NEIGHBOR,
            Solver.TWICE_AROUND,
            Solver.TWICE_AROUND_DIJKSTRA});
    }

    public TestStatisticsHelper(Solver[] algorithms) {
        this.data = new HashMap<>();

        for (Solver a : algorithms) {
            Map<String, Float> s = new HashMap<>();

            s.put("cost", 0f);
            s.put("time", 0f);
            s.put("best", 0f);

            this.data.put(a, s);
        }
    }

    public TestStatisticsHelper bestThisIteration(Solver a) {
        return add(a, "best", 1f);
    }

    public TestStatisticsHelper add(Solver s, String feature, float value) {
        data.get(s).put(feature, data.get(s).get(feature) + value);

        return this;
    }
    
    public Map<String, Float> solverMean(Solver s, int iterations) {
        Map<String, Float> means = new HashMap<>();
        
        data.get(s).entrySet().stream().forEach((entry) -> {
            means.put(entry.getKey(), entry.getValue() / iterations);
        });
        
        return means;
    }

    public float featureMean(Solver s, String feature, int iterations) {
        return data.get(s).get(feature) / iterations;
    }

    public Map<String, Float> getSolverFeatures(Solver s) {
        return data.get(s);
    }
}
