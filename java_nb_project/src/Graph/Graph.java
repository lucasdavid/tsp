package Graph;

import AdjMatrix.*;

/**
 *
 * @author lucasdavid
 */
public abstract class Graph {

    protected AdjMatrix m;
    protected Algorithms a;

    public Graph() {
        m = new AdjMatrix();
        a = new Algorithms();
    }

    public Graph(int _nodes) {
        m = new AdjMatrix(_nodes);
        a = new Algorithms();
    }

    public Graph(double[][] _tMatrix, int _nodes) {
        m = new AdjMatrix(_tMatrix, _nodes);
        a = new Algorithms();
    }

    public int getNodes() throws IllegalAdjMatrix {
        if (!m.validContext) {
            throw new IllegalAdjMatrix("can not recovery nodes property from a non-initialized graph");
        }
        return m.nodes;
    }

    public boolean getValidContext() {
        return m.validContext;
    }

    public double at(int _nodeA, int _nodeB)
            throws IllegalAdjMatrix, IndexOutOfBoundsException, IllegalArgumentException {

        if (!m.validContext) {
            throw new IllegalAdjMatrix("can not recovery adjacent matrix from a non-initialized graph");
        }

        if (_nodeA < 0 || _nodeB < 0 || _nodeA >= m.nodes || _nodeB >= m.nodes) {
            throw new IndexOutOfBoundsException(
                    "adjacent matrix bounds are " + m.nodes
                    + ", while coordinates required were [" + _nodeA + "][" + _nodeB + "]");
        }

        return m.m[_nodeA][_nodeB];
    }
}