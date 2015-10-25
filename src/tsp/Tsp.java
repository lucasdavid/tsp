package tsp;

import graph.Graph;

/**
 *
 * @author lucasdavid
 */
public class Tsp extends Graph {

    public final int MAXIMUM_COORDINATE = 100;

    public Tsp(float _m[][]) {
        m = _m;
    }

    /**
     * Create an Euclidean instance of the S-TSP problem.
     *
     * @param nodes number of nodes for the graph built will have.
     */
    public Tsp(int nodes) {
        if (nodes < 4) {
            throw new IllegalArgumentException(
                "TSP is not applicable for graph instances with " + nodes + " nodes or below.");
        }

        // Build graph from the distance d(a, b) = ||a-b||_2
        // for n coordinates in the R^n.
        float[][] coordinates = new float[nodes][3];

        for (float[] c : coordinates) {
            c[0] = (float) Math.random() * MAXIMUM_COORDINATE;
            c[1] = (float) Math.random() * MAXIMUM_COORDINATE;
            c[2] = (float) Math.random() * MAXIMUM_COORDINATE;
        }

        m = new float[nodes][nodes];
        for (int i = 0; i < m.length; i++) {
            m[i][i] = 0;
            for (int j = i + 1; j < m.length; j++) {
                m[j][i] = m[i][j] = euclideanDistance(coordinates[i], coordinates[j]);
            }
        }
    }

    /**
     * Create a non Euclidean instance of the S-TSP problem.
     *
     * @param nodes number of nodes for the graph built will have.
     * @param min_edge_value superior constraint for edge values.
     * @param max_edge_value inferior constraint for edge values.
     */
    public Tsp(int nodes, int min_edge_value, int max_edge_value) {
        if (nodes < 4) {
            throw new IllegalArgumentException(
                "TSP is not applicable for graph instances with " + nodes + " nodes or below.");
        }

        m = new float[nodes][nodes];
        for (int i = 0; i < m.length; i++) {
            m[i][i] = 0;

            for (int j = i + 1; j < m.length; j++) {
                m[i][j] = m[j][i] = (float) (Math.random() * max_edge_value);

                if (m[i][j] < min_edge_value) {
                    m[i][j] = m[j][i] = m[i][j] + min_edge_value;

                    if (m[i][j] > max_edge_value) {
                        m[i][j] = m[j][i] = m[i][j] + max_edge_value;
                    }
                }
            }
        }
    }

    /**
     * Return the Euclidean distance for two points in the R^N.
     *
     * @param a point in the R^n.
     * @param b point in the R^n.
     * @return the euclidian distance d(a,b) = ||a-b||_2.
     */
    protected final float euclideanDistance(float[] a, float[] b) {
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result += (a[i] - b[i]) * (a[i] - b[i]);
        }

        return (float) Math.sqrt(result);
    }

    public int nodes() {
        return m.length;
    }

    @Override
    public String toString() {
        String print = "";

        for (int i = 0; i < m.length; i++) {
            for (int j = i + 1; j < m[i].length; j++) {
                print += m[i][j] + " ";
            }
            print += '\n';
        }
        print += "---\n" + m.length + " nodes\n\n";

        return print;
    }
}
