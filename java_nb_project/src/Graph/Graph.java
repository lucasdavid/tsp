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

    public Graph(int[][] _tMatrix, int _nodes) {
        m = new AdjMatrix(_tMatrix, _nodes);
        a = new Algorithms();
    }

    public int getNodes() throws InvalidMatrix {
        if (!m.validContext) {
            throw new InvalidMatrix();
        }
        return m.nodes;
    }

    public boolean getValidContext() throws InvalidMatrix {
        if (!m.validContext) {
            throw new InvalidMatrix();
        }
        return m.validContext;
    }

    public int at(int _nodeA_ID, int _nodeB_ID) throws InvalidMatrix {
        if (!m.validContext) {
            throw new InvalidMatrix();
        }

        if (_nodeA_ID < 0 || _nodeA_ID > m.nodes || _nodeB_ID < 0 || _nodeB_ID > m.nodes) {
            throw new IllegalArgumentException();
        }

        return m.m[_nodeA_ID][_nodeB_ID];
    }
}