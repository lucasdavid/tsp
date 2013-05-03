package AdjMatrix;

/**
 *
 * @author lucasdavid
 */
public class AdjMatrix {

    public boolean validContext;
    public int nodes;
    public double m[][];

    public AdjMatrix() {
        validContext = false;
    }

    public AdjMatrix(int _nodes) {
        setM(new double[_nodes][_nodes], _nodes);
    }

    public AdjMatrix(double[][] _m, int _nodes) {
        setM(_m, _nodes);
        validContext = true;
    }

    private void setM(double[][] _m, int _nodes) throws IllegalArgumentException {
        if (_m == null || _nodes < 0) {
            validContext = false;
            throw new IllegalArgumentException();
        }

        m = _m;
        nodes = _nodes;
    }
}