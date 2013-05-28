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
    Tsp western("berlin52");
    western.lerGrDoArq("wi");
    cout << "Custo do circuito por twice-around: " << western.resTwiAroundOrig();
    
    /// * comparacao entre os twice-arounds
    Tsp t;
    t.cmpTwiAround(1000, 100);
    
    Tsp tsp("src/problemas/bayg29");
    Tsp tsp("src/problemas/berlin52");
    Tsp tsp("src/problemas/ch130");
    Tsp tsp("src/problemas/dantzig42");
    Tsp tsp("src/problemas/ali535.tsp");

    cout << "\nTwice-around original: " << tsp.resTwiAroundOrig() << endl
         << "Twice-around Dijkstra: " << tsp.resTwiAroundDijk() << endl
         << "Ocorr Shortest - path: " << tsp.resOcorrEmSPT() << endl;
     
    /// * comparacao entre o twice-around original e o algoritmo de ocorrencia em shortest-path trees.
    Tsp t;
    t.cmpTwiAroundEOcorrSPT(1000, 100);
    */
    
    Tsp tsp("src/problemas/bayg29");    
    cout //<< "\nTwice-around original: " << tsp.resTwiAroundOrig() << endl
         //<< "Twice-around Dijkstra: " << tsp.resTwiAroundDijk() << endl;
         << "Ocorr Shortest - path simplificado: " << tsp.resOcorrEmSPTSimplificado() << endl;
         //<< "Ocorr Shortest - path: " << tsp.resOcorrEmSPT() << endl;
    
    return 0;
}