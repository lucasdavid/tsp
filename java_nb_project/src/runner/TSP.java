package runner;

import Analyzer.*;

/**
 *
 * @author lucasdavid
 */
public class TSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Analyzer a = new Analyzer();
            a.Run();
        } catch (Exception e) {
            System.err.println(
                    "Class: " + e.getClass()
                    + "\nCause: " + e.getCause()
                    + "\nMessage: " + e.getMessage());
        }
    }
}
