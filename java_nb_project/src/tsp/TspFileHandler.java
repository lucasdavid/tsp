package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Date;

/**
 *
 * @author david
 */
public class TspFileHandler {

    private String tspFileName;
    private String reportFileName;
    private PrintWriter pw;
    private BufferedReader br;

    public TspFileHandler() {
        br = null;
        pw = null;
        reportFileName = "reports/report" + (new Date()).toString();
    }

    public TspFileHandler(String _problem) {
        br = null;
        pw = null;
        tspFileName = "src/problems/" + _problem;
        reportFileName = "src/reports/report " + (new Date()).toString();
    }

    public TspFileHandler(String _problem, String _report) {
        br = null;
        pw = null;
        tspFileName = "problems/" + _problem;
        reportFileName = "reports/" + _report;
    }

    public void InitReader() throws Exception {
        InitReader(tspFileName);
    }

    public void InitReader(String _fileName) throws Exception {
        if (br != null) {
            throw new Exception("duplicated reader");
        }

        br = new BufferedReader(new FileReader("src/problems/berlin52.tsp"));
    }

    public void InitWritter() throws Exception {
        if (pw != null) {
            throw new Exception("duplicated writter");
        } else {
            pw = new PrintWriter(new FileWriter(reportFileName));
        }
    }

    public double[][] Read() throws IOException {
        String sCurrentLine;
        double graph[][];
        double nodes[];

        graph = null;
        nodes = null;

        while ((sCurrentLine = br.readLine()) != null) {
            System.out.println(sCurrentLine);
        }

        return graph;
    }

    public void Append(String _line) {
        pw.println(_line);
        pw.flush();
    }

    public void CloseReader() throws IOException {
        br.close();
        br = null;
    }

    public void CloseWritter() {
        pw.close();
    }
}
