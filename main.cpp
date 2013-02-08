/* 
 * File:   main.cpp
 * Author: david
 *
 * Created on February 5, 2013, 11:07 PM
 */

#include <cstdlib>
#include <fstream>
#include <string>
#include "Tsp.h"

using namespace std;

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
    */
    
    Tsp western("src/berlin52");
    //western.lerGrDoArq("src/wi.txt");
    //western.gerarGrRand();
    western.resolver();
    
    //Tsp inst1;
    //inst1.gerarGrRand(5);
    //inst1.resolver();
    
    return 0;
}

