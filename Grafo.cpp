/* 
/* 
 * File:   Grafo.cpp
 * Author: david
 * 
 * Created on February 5, 2013, 11:08 PM
 */

#include "Grafo.h"
#include <cstdlib>
#include <time.h>
#include <iostream>

using namespace std;

Grafo::Grafo() {
    valido = false;
    matrizAdj = NULL;

    for (int i = 0; i < NUM_ALG; i++)
        alg[i] = NULL;
}
Grafo::Grafo(int _numVert) {
    valido = false;
    matrizAdj = NULL;

    for (int i = 0; i < NUM_ALG; i++)
        alg[i] = NULL;

    gerarMatRand(_numVert);
}
Grafo::Grafo(const Grafo& orig) {
    // TODO
}
Grafo::~Grafo() {
    reiniciar();
}

bool Grafo::reiniciar() {
    if (valido == false)
        return false; // o grafo nao foi inicializado.
    else {
        // rm matriz de adj do grafo original.
        for (int i = 0; i < numVert; i++)
            delete [] matrizAdj[i];
        delete [] matrizAdj;
        matrizAdj = NULL;

        // remove todas as matrizes de algoritmos que possivelmente foram aplicados.
        for (int i = 0; i < NUM_ALG; i++)
            if (alg[i] != NULL) {
                for (int j = 0; j < 4; j++)
                    delete [] alg[i][j];
                delete [] alg[i];

                alg[i] = NULL;
            }

        valido = false;
        return true;
    }
}
bool Grafo::gerarMatRand(int _numVert) {
    // numero de vertices invalido, nada a fazer
    if (_numVert < 1)
        return false;

    // ja existe uma matriz alocada. Vamos substitui-la.
    reiniciar();
    matrizAdj = new int *[_numVert];
    for (int i = 0; i < _numVert; i++) {
        matrizAdj[i] = new int[_numVert];
    }

    srand(time(0));
    for (int i = 0; i < _numVert; i++)
        for (int j = i + 1; j < _numVert; j++) {
            matrizAdj[i][j] = matrizAdj[j][i] = rand() % INTERV_DESVIO;

            if (matrizAdj[i][j] < MIN_AREST)
                matrizAdj[i][j] = matrizAdj[j][i] = MIN_AREST;
        }

    numVert = _numVert;
    return valido = true;
}
void Grafo::exibirMat(int _alg) {
    if (valido == false)
        cout << "Erro: o objeto nao e um grafo valido." << endl;
    else {

        int **tmpMat = (_alg == -1)
                ? matrizAdj
                : alg[_alg];

        if (_alg == -1)
            cout << "== Grafo orig. ==";
        else if (_alg == 0)
            cout << "== Prim ==";
        else if (_alg == 1)
            cout << "== Dijkstra ==";
        else
            cout << "== Twice-around ==";
        cout << endl;
        
        if (tmpMat == NULL)
            cout << "Matriz invalida";
        else
            for (int i = 0; i < numVert; i++) {
                for (int j = 0; j < numVert; j++)
                    cout << tmpMat[i][j] << " ";
                cout << endl;
            }

        cout << endl;
    }
}
int get(char _campo[]) {
    // TODO
}