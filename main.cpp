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
    int **chm = g.twiceAround();
    g.exibirMat(0); // exibir MST
    g.exibirMat(1); // exibir arv de caminhos minimos
    g.exibirMat(2); // exibir circuito hamiltoniano

    Tsp inst1;
    inst1.gerarGrRand(5);
    inst1.resolver();
    
    Tsp western("src/berlin52");
    western.lerGrDoArq("src/wi.txt");
    */
    Tsp t;
    t.cmpEntreTwiceArounds(10, 100000);
    
    return 0;
}

