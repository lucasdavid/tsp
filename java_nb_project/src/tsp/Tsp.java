package tsp;

import Graph.*;
import AdjMatrix.IllegalAdjMatrix;
/**
 *
 * @author lucasdavid
 */
public class Tsp extends Graph {

    public Tsp(int _nodes) {
        super(_nodes);
        if (_nodes < 4) {
            throw new IllegalArgumentException(
                    "TSP is not applicable for graph instances with " + _nodes + " nodes or below.");
        }

        RandomInit(_nodes);
    }

    public Tsp(String _fileMatrix) {
        //super(_m, _nodes);
    }

    private boolean RandomInit(int _nodes) {
        
        if (m.validContext) {
            throw new IllegalAdjMatrix();
        }

        for (int i = 0; i < m.m.length; i++) {
            for (int j = 0; j < m.m.length; j++) {
                m.m[i][j] = Math.random() * 100;
            }
        }

        return false;
    }

    private int[][] ReadFromFile(String _file) {
        return null;
    }

    public String report() {
        return new String();
    }

    public void print() {
        for (int i = 0; i < m.m.length; i++) {
            for (int j = 0; j < m.m.length; j++) {
                System.out.print(m.m[i][j] + " ");
            }
            System.out.println();
        }
    }
}
