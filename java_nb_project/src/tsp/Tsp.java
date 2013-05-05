package tsp;

import Graph.*;
import AdjMatrix.IllegalAdjMatrixOperation;

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
        m.validContext = true;
    }

    public Tsp(String _fileMatrix) {
        //super(_m, _nodes);
    }

    private void RandomInit(int _nodes) {

        if (m.validContext) {
            throw new IllegalAdjMatrixOperation();
        }

        for (int i = 0; i < m.m.length; i++) {
            for (int j = 0; j < m.m.length; j++) {
                m.m[i][j] = (int) (Math.random() * 100);
            }
        }
    }

//    private int[][] ReadFromFile(String _file) {
//        return null;
//    }
    public String report() {
        return new String();
    }

    public void print() {
        System.out.println(m.nodes + " nodes\n---");

        String str = "|\t";

        for (int i = 0; i < m.m.length; i++) {
            for (int j = 0; j < m.m.length; j++) {
                str += m.m[i][j] + "\t";
            }

            System.out.println(str + "|");
            str = "|\t";
        }

        System.out.println("\n");
    }
}
