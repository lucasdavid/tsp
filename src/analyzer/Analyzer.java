package analyzer;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import tsp.*;

public class Analyzer {

    final int ITERACTIONS = 100;
    final int MAX_NODES = 1000;
    final int MIN_NODES = 4;
    final int MAX_EDGE_VALUE = 100;
    final int MIN_EDGE_VALUE = 1;
    final boolean PRINT_MATRIX = false;
    final boolean GENERATE_REPORT = true;

    Type type;
    String file;

    Tsp problem;
    TspFileHandler inout;
    int iteraction = 1;
    Solver[] compared;

    TestStatisticsHelper statistics;

    public Analyzer() {
        this(new Solver[]{Solver.TWICE_AROUND, Solver.TWICE_AROUND_DIJKSTRA},
            Type.INCREASING_SIZE_RANDOM_GRAPH, "report.data");
    }

    public Analyzer(String file) {
        this(new Solver[]{Solver.TWICE_AROUND, Solver.TWICE_AROUND_DIJKSTRA},
            Type.FILE_LOADED, file);
    }

    public Analyzer(Solver[] solvers, Type type, String file) {
        if (solvers.length == 0) {
            throw new RuntimeException("Cannot create Analyzer without solvers");
        }

        this.compared = solvers;
        this.type = type;
        this.file = file;

        this.statistics = new TestStatisticsHelper(solvers);
    }

    public void run() throws Exception {
        System.out.println("Analyzer has started.");

        inout = new TspFileHandler(file);
        inout.append("# Feature order: solver name, Cost, Time.\n");

        if (type == Type.FILE_LOADED) {
            float m[][] = inout.read();
            problem = new Tsp(m);

            if (PRINT_MATRIX) {
                System.out.print(problem);
            }
            compare();
        } else {
            Random r = new Random();

            for (iteraction = 0; iteraction < ITERACTIONS; iteraction++) {
                int nodes = type == Type.RANDOM_GRAPH
                    ? r.nextInt((MAX_NODES - MIN_NODES)) + MIN_NODES
                    : (int)(((float)iteraction * (MAX_NODES - MIN_NODES)) / ITERACTIONS + MIN_NODES);

                problem = new Tsp(nodes, MAX_EDGE_VALUE, MIN_EDGE_VALUE);

                if (PRINT_MATRIX) {
                    System.out.print(problem);
                }

                compare();
            }
        }

        if (GENERATE_REPORT) {
            inout.append("\n# Conclusion");

            for (Solver s : compared) {
                Map<String, Float> result = statistics.solverMean(s, iteraction);
                inout.append("# " + s + ": " + result.toString());
            }

            inout.commit();
        }

        System.out.println(".");
    }

    private void compare() throws Exception {
        if (GENERATE_REPORT) {
            inout.append(String.format("# Iteration: %d, cities: %d", iteraction, problem.nodes()));
        }

        Solver bestSolver = compared[0];
        float bestCost = Float.MAX_VALUE;

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

            if (GENERATE_REPORT) {
                inout.append(String.format("%s,%f,%d", s, cost, timeit));
            }
        }

        statistics.bestThisIteration(bestSolver);

        inout.append("");
    }
}
