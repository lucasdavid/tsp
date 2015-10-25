package runner;

import analyzer.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucasdavid
 */
public class TSP {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        analyzeRandomEuclideanProblems();
        analyzeRealProblems();
        analyzeRandomNonEuclideanProblems();
    }

    public static void analyzeRealProblems() throws IOException {
        Files.walk(Paths.get("src/problems")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {

                    System.out.println(String.format("Benchmarking graph %s.", filePath));
                    new Analyzer(filePath.getFileName().toString()).run();

                } catch (Exception ex) {
                    Logger.getLogger(TSP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void analyzeRandomNonEuclideanProblems() throws Exception {
        System.out.println("Benchmarking random non-euclidean problems...");
        Analyzer a = new Analyzer();
        a.use_euclidean_graphs = false;
        a.run();
    }

    public static void analyzeRandomEuclideanProblems() throws Exception {
        System.out.println("Benchmarking random euclidean problems...");
        new Analyzer().run();
    }
}
