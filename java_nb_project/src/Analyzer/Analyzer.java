package Analyzer;

import tsp.*;

/**
 *
 * @author david
 */
public class Analyzer {

    private final int INTERACTIONS = 1;
    private final int MAX_NODES = 4;
    private final int MIN_NODES = 4;
    private final int MAX_EDGE_VALUE = 10;
    private final int MIN_EDGE_VALUE = 1;
    private final String GRAPH_FILE = "bayg29.tsp";
    private final boolean PRINT_PROBLEMS = true;
    private final boolean GENERATE_REPORT = false;

    private enum algorithms {

        NEARESTNEIGHBOR, TWICEAROUND, TWICEAROUNDDIJK, EDGESCORE
    }
    private Tsp currentProblem;
    private double[] algorithmsCosts;
    private algorithms[] algorithmsCompared;
    private TspFileHandler inout;

    public void run() {
        inout = new TspFileHandler(GRAPH_FILE);

        // Alter the comparing algorithms here
        algorithmsCompared = new algorithms[]{
            algorithms.NEARESTNEIGHBOR,
            algorithms.TWICEAROUND,
            algorithms.TWICEAROUNDDIJK,
            algorithms.EDGESCORE
        };

        algorithmsCosts = new double[algorithmsCompared.length];

        //fileGraphComparation();
        try {
        randomComparation();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void fileGraphComparation() throws Exception {
        inout.initReader();
        if (GENERATE_REPORT) {
            inout.initWriter();
        }

        double m[][] = inout.read();
        currentProblem = new Tsp(m, m.length);
        if (PRINT_PROBLEMS) {
            currentProblem.print();
        }
        compare();
    }

    private void randomComparation() throws Exception {
        for (int i = 0; i < INTERACTIONS; i++) {
            currentProblem = new Tsp(genRandomNodeCount(), MAX_EDGE_VALUE, MIN_EDGE_VALUE);
            if (PRINT_PROBLEMS) {
                currentProblem.print();
            }
            compare();
        }
    }

    private int genRandomNodeCount() {
        int tNodes = (int) (Math.random() * 100) % MAX_NODES;
        if (tNodes < MIN_NODES) {
            tNodes += MIN_NODES;
            if (tNodes > MAX_NODES) {
                tNodes = MAX_NODES;
            }
        }

        return tNodes;
    }

    private void compare() {
        inout.append("**Analyses report**\n");
        inout.append("Algorithms compared\n==================");

        for (int i = 0; i < algorithmsCompared.length; i++) {
            inout.append(algorithmsCompared[i].toString());

            if (algorithmsCompared[i] == algorithms.NEARESTNEIGHBOR) {
                algorithmsCosts[i] = currentProblem.NearestNeighbor();

            } else if (algorithmsCompared[i] == algorithms.TWICEAROUND) {
                algorithmsCosts[i] = currentProblem.TwiceAround();

            } else if (algorithmsCompared[i] == algorithms.TWICEAROUNDDIJK) {
                algorithmsCosts[i] = currentProblem.TwiceAroundDijkstra();

            } else if (algorithmsCompared[i] == algorithms.EDGESCORE) {
                algorithmsCosts[i] = currentProblem.EdgeScore();
            }
        }

        System.out.println();
        inout.append("\nAlgorithms result\n==================");

        int ret = 0;
        for (int i = 0; i < algorithmsCompared.length; i++) {
            System.out.println(algorithmsCompared[i] + ": " + algorithmsCosts[i]);
            inout.append(algorithmsCompared[i] + ": " + algorithmsCosts[i]);

            if (algorithmsCosts[i] < algorithmsCosts[ret]) {
                ret = i;
            }
        }

        System.out.println("\nBest result: " + algorithmsCompared[ret] + "\n");
        inout.append("\nBest result\n==================");
        inout.append(algorithmsCompared[ret].toString());

        inout.closeWriter();
    }
}