package Analyzer;

import tsp.*;

/**
 *
 * @author david
 */
public class Analyzer {

    private enum algorithmsNames {

        TWICEAROUND, TWICEAROUNDDIJK, EDGESCORE
    }

    public void Run() {
        Tsp tsp = new Tsp(4);
    }

    private void compare(String[] _selectedAlgorithms) {
        for (int i = 0; i < _selectedAlgorithms.length; i++) {
            double ret = report();
            
        }
    }
    private double report() {
        return double;
    }

    private void switchCurrentAlgorithm() {
    }
}