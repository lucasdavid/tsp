package AdjMatrix;

/**
 *
 * @author david
 * Exception IllegalAdjMatrixOperation
 */
public class IllegalAdjMatrixOperation extends RuntimeException {

    public IllegalAdjMatrixOperation() {
        super();
    }
    
    public IllegalAdjMatrixOperation(String message) {
        super(message);
    }

    public IllegalAdjMatrixOperation(Throwable cause) {
        super(cause);
    }
}
