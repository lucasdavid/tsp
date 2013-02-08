/* 
 * File:   main.cpp
 * Author: david
 *
 * Created on February 5, 2013, 11:07 PM
 */

#include <cstdlib>
#include <iostream>
#include <fstream>
#include <string>
#include "Grafo.h"

using namespace std;

void lerCidade() {
    string linha;
    ifstream cidade("src/wi.txt");
    if (cidade.is_open()) {
        while (cidade.good()) {
            getlinha(cidade, linha);
            cout << linha[0] << endl;
            //cout << linha << endl;
        }
        cidade.close();
    } else cout << "Unable to open file";
}

int main(int argc, char** argv) {

    /*Grafo g;
    g.gerarMatRand(4);
    g.exibirMat();

    //int **acm = g.dijkstra();
    //int **mst = g.prim();
    int **chm = g.twiceAround();
    //g.exibirMat(0); // exibir MST
    //g.exibirMat(1); // exibir arv de caminhos minimos
    g.exibirMat(2); // exibir circuito hamiltoniano*/
    lerCidade();

    return 0;
}

