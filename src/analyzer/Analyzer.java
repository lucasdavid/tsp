package Analyzer;

import tsp.*;

/**
 *
 * @author david
 */
public class Analyzer {

    final int INTERACTIONS = 100;
    final int MAX_NODES = 5;
    final int MIN_NODES = 4;
    final int MAX_EDGE_VALUE = 100;
    final int MIN_EDGE_VALUE = 1;
    final boolean PRINT_MATRIX = false;
    final boolean GENERATE_REPORT = true;

    AnalysesType type;
    String file;

    Tsp problem;
    TspFileHandler inout;
    int currInter;
    int[] counters;
    double[] costs;
    Algorithms[] compared;

    public Analyzer() {
        this(AnalysesType.RANDOM, "report.data");
    }

    public Analyzer(AnalysesType type, String file) {
        this.type = type;
        this.file = file;
    }

    public void run() throws Exception {
        System.out.println("Twice-around benchmarking has started.");

        inout = new TspFileHandler(file);

        compared = new Algorithms[]{
            Algorithms.NEARESTNEIGHBOR,
            Algorithms.TWICEAROUND,
            Algorithms.TWICEAROUNDDIJK,};

        costs = new double[compared.length];
        counters = new int[compared.length];

        for (int i = 0; i < counters.length; i++) {
            counters[i] = 0;
        }

        if (type == AnalysesType.FILE_LOADED) {
            double m[][] = inout.read();
            problem = new Tsp(m);

            if (PRINT_MATRIX) {
                System.out.print(problem);
            }
            compare();
        } else {
            // for each defined interaction
            for (currInter = 0; currInter < INTERACTIONS; currInter++) {
                int tNodes = (int) (Math.random() * MAX_NODES);

                if (tNodes < MIN_NODES) {
                    tNodes += MIN_NODES;

                    if (tNodes > MAX_NODES) {
                        tNodes = MAX_NODES;
                    }
                }
                // randomly generates a instance of TSP
                problem = new Tsp(tNodes, MAX_EDGE_VALUE, MIN_EDGE_VALUE);
                if (PRINT_MATRIX) {
                    System.out.print(problem);
                }
                // increase the score of the best method
                counters[compare()]++;
            }
        }

        if (GENERATE_REPORT && type == AnalysesType.RANDOM) {
            inout.append("Conclusion - shortest circuit count:");

            for (int i = 0; i < compared.length; i++) {
                inout.append(compared[i] + ": " + counters[i]);
            }
            inout.commit();
        }

        System.out.println("Finished.");
    }

    private int compare() throws Exception {
        if (GENERATE_REPORT) {
            inout.append("> inst " + currInter + '\n');
            inout.append("Number of cities: " + problem.nodes());
        }

        for (int i = 0; i < compared.length; i++) {
            if (compared[i] == Algorithms.NEARESTNEIGHBOR) {
                costs[i] = problem.NearestNeighbor();
            } else if (compared[i] == Algorithms.TWICEAROUND) {
                costs[i] = problem.TwiceAround();
            } else if (compared[i] == Algorithms.TWICEAROUNDDIJK) {
                costs[i] = problem.TwiceAroundDijkstra();
            }
        }

        int ret = 0;
        for (int i = 0; i < compared.length; i++) {
            if (GENERATE_REPORT) {
                inout.append(compared[i] + ": " + costs[i]);
            }
            if (costs[i] < costs[ret]) {
                ret = i;
            }
        }

        if (GENERATE_REPORT) {
            inout.append("\nBest result: " + compared[ret].toString() + "\n");
        }
        // returns the method which gave the shortest solution
        return ret;
    }
}
