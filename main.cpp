/* 
 * File:   main.cpp
 * Author: david
 *
 * Created on February 5, 2013, 11:07 PM
 */

#include "Tsp.h"
#include <iostream>

using std::cout;
using std::endl;
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
    
    /// * leitura e resolucao do TSP berlin52
    Tsp western("src/berlin52");
    western.lerGrDoArq("src/wi.txt");
    cout << "Custo do circuito por twice-around: " << western.resTwiAroundOrig();
    
    /// * comparacao entre os twice-arounds
    Tsp t;
    t.cmpTwiAround(1000, 100);
    
    Tsp t(5);
    t.resNovoMetodoACM();
    */
    
    Tsp t;
    t.cmpTwiAroundEOcorrSPT(1000, 100);
    
    //Tsp western("src/berlin52");
    //cout << "Twice-around:  " << western.resTwiAroundOrig() << endl
    //     << "Twice-around:  " << western.resTwiAroundDijk() << endl
    //     << "Shortest-path: " << western.resOcorrEmSPT() << endl;
    
    return 0;
}