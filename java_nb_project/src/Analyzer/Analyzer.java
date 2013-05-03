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

    private Tsp currentProblem;
    private final int INTERACTIONS = 1;
    private final int MAX_NODES = 15;
    private final int MIN_NODES = 4;
    private double[] algorithmsCosts;
    private algorithms[] algorithmsCompared;

    public void Run() {

        // Alter the comparing algorithms here
        algorithmsCompared = new algorithms[]{
            algorithms.TWICEAROUND,
            algorithms.EDGESCORE
        };

        algorithmsCosts = new double[algorithmsCompared.length];
        int tNodes;
        for (int i = 0; i < INTERACTIONS; i++) {
            tNodes = (int) (Math.random() * 100) % MAX_NODES;

            if (tNodes < MIN_NODES) {
                tNodes += MIN_NODES;
                if (tNodes > MAX_NODES) {
                    tNodes = MAX_NODES;
                }
            }

            currentProblem = new Tsp(tNodes);
            currentProblem.print();

            compare();
        }
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

    private double report(algorithms _currentAlgorithm) {
        double ret = -1;
        return ret;
    }
}