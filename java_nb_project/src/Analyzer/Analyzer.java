package Analyzer;

import tsp.*;

/**
 *
 * @author david
 */
enum algorithms {

    TWICEAROUND, TWICEAROUNDDIJK, EDGESCORE
}

public class Analyzer {

    private final int INTERACTIONS = 1;
    private final int MAX_NODES = 15;
    private final int MIN_NODES = 4;
    private final String GRAPH_FILE = "bayg29.tsp";
    private Tsp currentProblem;
    private double[] algorithmsCosts;
    private algorithms[] algorithmsCompared;
    private TspFileHandler inout;

    public void Run() throws Exception {
        // Alter the comparing algorithms here
        algorithmsCompared = new algorithms[]{
            algorithms.TWICEAROUND,
            algorithms.EDGESCORE
        };

        algorithmsCosts = new double[algorithmsCompared.length];
        
        FileGraphComparation();
        //RandomComparation();
    }
    
    private void FileGraphComparation() throws Exception {
        inout = new TspFileHandler(GRAPH_FILE);
        inout.InitReader();
        //inout.InitWriter();
        
        double m[][] = inout.Read();
        currentProblem = new Tsp(m, m.length);
        currentProblem.print();
        compare();
    }
    
    private void RandomComparation() throws Exception {
        for (int i = 0; i < INTERACTIONS; i++) {
            currentProblem = new Tsp(genRandomNodeCount());
            currentProblem.print();
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
        for (int i = 0; i < algorithmsCompared.length; i++) {
            if (algorithmsCompared[i] == algorithms.TWICEAROUND) {
                algorithmsCosts[i] = currentProblem.TwiceAround();

            } else if (algorithmsCompared[i] == algorithms.TWICEAROUNDDIJK) {
                algorithmsCosts[i] = currentProblem.TwiceAroundDijkstra();

            } else if (algorithmsCompared[i] == algorithms.EDGESCORE) {
                algorithmsCosts[i] = currentProblem.EdgeScore();
            }
        }

        int ret = 0;
        for (int i = 1; i < algorithmsCompared.length; i++) {
            if (algorithmsCosts[i] < algorithmsCosts[ret]) {
                ret = i;
            }
        }
    }
}