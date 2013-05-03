package AdjMatrix;

/**
 *
 * @author david
 * Exception IllegalAdjMatrix
 */
public class IllegalAdjMatrix extends RuntimeException {

    public IllegalAdjMatrix() {
        super();
    }
    
    public IllegalAdjMatrix(String message) {
        super(message);
    }

    public IllegalAdjMatrix(Throwable cause) {
        super(cause);
    }
}
