/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import Graph.*;

/**
 *
 * @author lucasdavid
 */
public class Tsp extends Graph {

    public Tsp() {
        super();
    }
    
    public Tsp(int _nodes) {
        super(_nodes);
    }
    
    public Tsp(int[][] _m, int _nodes) {
        super(_m, _nodes);
    }

    private void toogleValidContext() {
    }

    private boolean RandomInit(int _nodes) {
        return false;
    }

    private int[][] ReadFromFile(String _file) {
        return null;
    }

    public String report() {
        return new String();
    }
}
