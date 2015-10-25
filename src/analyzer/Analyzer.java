package analyzer;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import tsp.*;

public class Analyzer {

    public int iteractions = 50;
    public int minimum_nodes = 4;
    public int maximum_nodes = 1000;
    public int minimum_edge_value = 1;
    public int maximum_edge_value = 100;
    public boolean use_euclidean_graphs = true;
    public boolean progressively_increase_number_of_cities = true;

    public boolean logging = false;
    public boolean reporting = true;

    final static Solver[] DEFAULT_SOLVERS = new Solver[]{Solver.TWICE_AROUND, Solver.TWICE_AROUND_DIJKSTRA};

    Source source;
    String file;

    Tsp problem;
    int iteration = 1;
    Solver[] compared;

    TspFileHandler fileHandler;
    TestStatisticsHelper statistics;

    public Analyzer() {
        this(Source.RANDOM);
    }

    public Analyzer(String file) {
        this(Source.FILE_LOADED, file);
    }

    public Analyzer(Source type) {
        this(DEFAULT_SOLVERS, type,
            String.format("report-%d.data", System.currentTimeMillis()));
    }

    public Analyzer(Source type, String file) {
        this(DEFAULT_SOLVERS, type, file);
    }

    public Analyzer(Solver[] solvers, Source type, String file) {
        if (solvers.length == 0) {
            throw new RuntimeException("Cannot create Analyzer without solvers");
        }

        this.compared = solvers;
        this.source = type;
        this.file = file;

        this.statistics = new TestStatisticsHelper(solvers);
    }

    public void run() throws Exception {
        System.out.println("Analyzer has started.");

        fileHandler = new TspFileHandler(file);

        String labels = "";
        for (Solver s : compared) {
            labels += s.toString() + " cost," + s.toString() + " time,";
        }
        
        fileHandler.append("# TSP Report " + file);
        fileHandler.append("# Iteractions: " + iteractions);
        fileHandler.append("# Source: " + source);
        fileHandler.append("# Euclidean: " + use_euclidean_graphs + "\n");
        
        fileHandler.append("Iteration,# Cities," + labels.substring(0, labels.length() - 1));

        switch (source) {
            case FILE_LOADED:
                analyzeLoadedGraphs();
                break;
            case RANDOM:
                analyzeRandomGraphs();
                break;
        }

        if (reporting) {
            fileHandler.append("\n# Conclusion");

            for (Solver s : compared) {
                Map<String, Float> result = statistics.solverMean(s, iteration);
                fileHandler.append("# " + s + ": " + result.toString());
            }

            fileHandler.commit();
        }

        System.out.println(".");
    }

    private void compare() throws Exception {
        Solver bestSolver = compared[0];
        float bestCost = Float.MAX_VALUE;

        String output = "";

        for (Solver s : compared) {
            long timeit = -System.nanoTime();

            float cost = s == Solver.NEAREST_NEIGHBOR ? problem.NearestNeighbor()
                : s == Solver.TWICE_AROUND ? problem.TwiceAround()
                    : s == Solver.TWICE_AROUND_DIJKSTRA ? problem.TwiceAroundDijkstra()
                        : 0f;

            timeit += System.nanoTime();

            statistics.add(s, "time", TimeUnit.NANOSECONDS.toMillis(timeit));
            statistics.add(s, "cost", cost);

            if (bestCost > cost) {
                bestSolver = s;
                bestCost = cost;
            }

            output += cost + "," + timeit + ",";
        }

        if (reporting) {
            fileHandler.append(String.format("%d,%d,%s", iteration, problem.nodes(),
                output.subSequence(0, output.length() - 1)));
        }

        statistics.bestThisIteration(bestSolver);
    }

    private void analyzeLoadedGraphs() throws Exception {
        float m[][] = fileHandler.read();
        problem = new Tsp(m);

        if (logging) {
            System.out.print(problem);
        }

        compare();
    }

    private void analyzeRandomGraphs() throws Exception {
        Random r = new Random();

        for (iteration = 0; iteration < iteractions; iteration++) {
            int nodes = progressively_increase_number_of_cities
                ? (int) (((float) iteration * (maximum_nodes - minimum_nodes)) / iteractions + minimum_nodes)
                : r.nextInt((maximum_nodes - minimum_nodes)) + minimum_nodes;

            problem = use_euclidean_graphs
                ? new Tsp(nodes)
                : new Tsp(nodes, minimum_edge_value, maximum_edge_value);

            if (logging) {
                System.out.print(problem);
            }

            compare();
        }
    }
}
