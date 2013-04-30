package AdjMatrix;

/**
 *
 * @author lucasdavid
 */
public class AdjMatrix {

    public boolean validContext;
    public int nodes, m[][];

    public AdjMatrix() {
        this.validContext = false;
    }

    public AdjMatrix(int _nodes) {
        this.validContext = false;

        if (_nodes < 0) {
            throw new ArrayIndexOutOfBoundsException(_nodes);
        }

        this.nodes = _nodes;
        this.m = new int[_nodes][_nodes];
        this.validContext = true;
    }

    public AdjMatrix(int[][] _tFileMatrix, int _nodes) {
        this.m = _tFileMatrix;
        this.validContext = true;
        this.nodes = _nodes;
    }

    public boolean setM(int[][] _m, int _nodes) {
        if (_m == null || _nodes < 0) {
            this.validContext = false;
            throw new IllegalArgumentException();
        }

        this.m = _m;
        this.nodes = _nodes;
        return this.validContext = true;
    }
}