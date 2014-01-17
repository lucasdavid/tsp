package tsp;

import Graph.Graph;

/**
 *
 * @author lucasdavid
 */
public class Tsp extends Graph {
    
    public Tsp(double _m[][]) {
        m = _m;
    }

    public Tsp(int _nodes, int _max_edge_value, int _min_edge_value) {
        if (_nodes < 4) {
            throw new IllegalArgumentException("TSP is not applicable for graph instances with " + _nodes + " nodes or below.");
        }

        m = new double[_nodes][_nodes];

        for (int i = 0; i < m.length; i++) {
            m[i][i] = 0;

            for (int j = i + 1; j < m.length; j++) {
                m[i][j] = m[j][i] = (double) (Math.random() * _max_edge_value);

                if (m[i][j] < _min_edge_value) {
                    m[i][j] = m[j][i] = m[i][j] + _min_edge_value;

                    if (m[i][j] > _max_edge_value) {
                        m[i][j] = m[j][i] = m[i][j] + _max_edge_value;
                    }
                }
            }
        }
    }

    public String report() {
        return new String();
    }

    public int nodes() {
        return m.length;
    }
    
    @Override
    public String toString() {
        String print = "";

        for (double[] m1 : m) {
            for (double m2 : m1) {
                print += m2 + "  ";
            }
            print += '\n';
        }
        print += "---\n" + m.length + " nodes\n\n";

        return print;
    }
}
