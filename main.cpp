/* 
 * File:   main.cpp
 * Author: david
 *
 * Created on February 5, 2013, 11:07 PM
 */


#include <cstdlib>
#include "Grafo.h"

using namespace std;

int main(int argc, char** argv) {
    
    Grafo g;
    g.gerarMatRand(4);
    g.exibirMat();
    
    //int **acm = g.dijkstra();
    //int **mst = g.prim();
    int **chm = g.twiceAround();
    g.exibirMat(0); // exibir MST
    g.exibirMat(2); // exibir circuito hamiltoniano
    
    return 0;
}

