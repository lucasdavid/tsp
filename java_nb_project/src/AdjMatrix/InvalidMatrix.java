package AdjMatrix;

/**
 *
 * @author lucasdavid
 */
public class InvalidMatrix extends Exception {

    public InvalidMatrix() {
        super("InvalidMatrix Exception invoked");
    }
    public InvalidMatrix(String message) {
        super(message);
    }
}