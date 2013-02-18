/* 
 * File:   main.cpp
 * Author: david
 *
 * Created on February 5, 2013, 11:07 PM
 */

#include "Tsp.h"

int main(int argc, char** argv) {

    /*
    Grafo g;
    g.gerarMatRand(4);
    g.exibirMat(); // exibe mat. de adj. do grafo original

    int **acm = g.dijkstra();
    int **mst = g.prim();
    g.exibirMat(0); // exibir MST
    g.exibirMat(1); // exibir arv de caminhos minimos
    g.exibirMat(2); // exibir circuito hamiltoniano

    Tsp inst1;
    inst1.gerarGrRand(5);
    
    Tsp western("src/berlin52");
    western.lerGrDoArq("src/wi.txt");
    cout << "Custo do circuito por twice-around: " << western.resTwiAroundOrig();
    */
    
    Tsp t(5);
    //t.cmpTwiAround(1000, 100);
    t.resNovoMetodoACM();
    
    return 0;
}