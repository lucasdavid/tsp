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
        setM(new double[_nodes][_nodes]);
    }

    public AdjMatrix(double[][] _m) {
        setM(_m);
        validContext = true;
    }

    private void setM(double[][] _m) throws IllegalArgumentException {
        if (_m == null) {
            validContext = false;
            throw new IllegalArgumentException();
        }

        m = _m;
        nodes = _m.length;
    }
}