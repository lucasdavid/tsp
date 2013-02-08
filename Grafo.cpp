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
int get(char _campo[]) {
    // TODO
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

        for (int i = 0; i < numVert; i++) {
            for (int j = 0; j < numVert; j++)
                cout << tmpMat[i][j] << " ";
            cout << endl;
        }

        cout << endl;
    }
}

int **Grafo::prim() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false)
        return NULL;

    // cria matriz de adjacencia da MST
    // e conjunto visitados auxiliar no algoritmo de Prim
    int **mst = new int*[numVert], *visitados = new int[numVert];
    int visitadosLim = 0, numArestVistas = 0, pesoArestMenor = 0, vertDestMenor, vertOrigMenor, vertOrig;
    int i, j, k;

    bool jaInserido;

    for (i = 0; i < numVert; i++) {
        mst[i] = new int[numVert];
        for (int j = 0; j < numVert; j++)
            mst[i][j] = 0;
    }

    // inicializa visitados com o vertice inicial
    // (vertice 0)
    visitados[0] = 0;
    visitadosLim++;

    // Insere arestas ate que seu numero seja numVert -1 (para quando o grafo e conexo).
    // Quando este e desconexo, pesoArestMenor assumira o valor -1 em algum momento,
    // e o algoritmo ira parar.
    while (numArestVistas < numVert -1 && pesoArestMenor != -1) {
        pesoArestMenor = -1;
        i = 0;
        while (i < visitadosLim) {
            vertOrig = visitados[i];

            for (j = 0; j < numVert; j++) {
                if (matrizAdj[vertOrig][j] != 0) { // existe uma aresta entre os dois vertices   
                    jaInserido = false;
                    k = 0;

                    while (k < visitadosLim && jaInserido == false) {
                        if (j == visitados[k])
                            jaInserido = true;
                        k++;
                    }

                    // se este vertice ainda nao foi inserido
                    // e esta aresta possui o menor peso encontrado
                    if (jaInserido == false && (matrizAdj[vertOrig][j] < pesoArestMenor || pesoArestMenor == -1)) {
                        vertOrigMenor = vertOrig;
                        vertDestMenor = j;
                        pesoArestMenor = matrizAdj[vertOrig][j];
                    }
                }
            }
            i++;
        }

        // uma aresta, que leva para um vertice ainda nao visitado foi encontrada.
        if (pesoArestMenor != -1) {
            // alcancamos um vertice, coloca-o no vetor de vertices visitados.
            visitados[visitadosLim] = vertDestMenor;
            visitadosLim++;

            // inserimos a aresta na matriz de adj. da MST
            mst[vertDestMenor][vertOrigMenor] = mst[vertOrigMenor][vertDestMenor] = pesoArestMenor;
        }
        numArestVistas++;
    }

    delete [] visitados;
    return alg[0] = mst; // retorna a MST
}
// FIXME
int **Grafo::dijkstra() {
    int **acm = new int*[numVert], // acm: matriz de adjacencia de uma arvore de caminhos minimos
        **Q = new int*[numVert],   // Q: vetor de vertices a serem analizados
        **visitados = new int*[numVert];

    int i, j, vertMenor[3], posMenor, visitadosLim;

    // declara arvores de caminhos minimos
    for (i = 0; i < numVert; i++) {
        acm[i] = new int[numVert];
        for (j = 0; j < numVert; j++)
            acm[i][j] = 0;
    }

    // inicializa conjunto de vertices que ainda devem ser visitados
    for (i = 0; i < numVert; i++) {
        Q[i] = new int[3];
        Q[i][0] = i;  // indice do vertice
        Q[i][1] = -1; // custo para alcanca-lo
        Q[i][2] = -1; // predecessor do vertice

        visitados[i] = new int[3];
    }
    Q[0][0] = 0;
    Q[0][1] = 0;
    Q[0][2] = 0;

    visitadosLim = numVert;
    while (visitadosLim > 0) {
        posMenor = 0;

        //  encontra o vertice de menor custo.
        //  -1 significa infinito.
        for (i = 1; i < visitadosLim; i++)
            if (Q[posMenor][0] == -1 || (Q[i][0] < Q[posMenor][0] && Q[i][0] != -1))
                posMenor = i;

        // retira o vertice do vetor, copiando o ultimo elemento
        // valido para a posicao do vertice retirado.
        vertMenor[0] = Q[posMenor][0];
        vertMenor[1] = Q[posMenor][1];
        vertMenor[2] = Q[posMenor][2];
        Q[posMenor][0] = Q[visitadosLim - 1][0];
        Q[posMenor][1] = Q[visitadosLim - 1][1];
        Q[posMenor][2] = Q[visitadosLim - 1][2];

        visitados[numVert - visitadosLim][0] = vertMenor[0];
        visitados[numVert - visitadosLim][1] = vertMenor[1];
        visitados[numVert - visitadosLim][2] = vertMenor[2];

        // relaxamento de arestas
        for (i = 0; i < visitadosLim; i++) {
            if (
                matrizAdj[ vertMenor[0] ][ Q[i][0] ] > 0 // para todos os vertices adjacentes que ainda nao foram retirados
                && (
                    // o vertice atual nao foi alcancado, possui custo infinito
                    // ou um caminho de menor custo encontrado.
                    Q[i][1] == -1 || Q[i][1] > vertMenor[1] + matrizAdj[ vertMenor[0] ][ Q[i][0] ]
                )
            ) {
                Q[i][1] = vertMenor[1] + matrizAdj[ vertMenor[0] ][ Q[i][0] ];
                Q[i][2] = vertMenor[0];
            }
        }

        visitadosLim--;
    }

    for (i = 0; i < numVert; i++) {
        acm[visitados[i][0]][visitados[i][2]] = matrizAdj[visitados[i][0]][visitados[i][2]];
        acm[visitados[i][2]][visitados[i][0]] = matrizAdj[visitados[i][2]][visitados[i][0]];
    }

    for (i = 0; i < numVert; i++) {
        delete [] Q[i];
        delete [] visitados[i];
    }
    delete [] Q;
    delete [] visitados;

    return alg[1] = acm;
}
int **Grafo::twiceAround() {
    int **mst, // arvore spanning minima
        *lstVert, // lista de vertices visitados pelo DFS
        lstLim = 2*numVert -1,
        
        **chm = new int*[numVert], // matriz adj. do circuito hamiltoniano
        i, j;
    int custo = 0;
    
    mst = prim();
    lstVert = DFS(mst);
    
    for (i = 0; i < numVert; i++) {
        chm[i] = new int[numVert];
        for (j = 0; j < numVert; j++)
            chm[i][j] = 0;
    }
    
    //exibirMat(0);
    /*cout << "== DFS ==" << endl;
    for (i = 0; i < lstLim; i++)
        cout << lstVert[i] << " ";
    cout << "\n\n";*/
    
    i = 0;
    while (i < lstLim -1) {
        int j = i +1;
        
        while (j < lstLim -1)
            if (lstVert[j] != lstVert[i])
                j++;
            else {
                for (int k = j; k < lstLim -1; k++)
                    lstVert[k] = lstVert[k +1];
                lstLim--;
            }
        i++;
    }
    
    /*cout << "== Enxugamento ==" << endl;
    for (i = 0; i < lstLim; i++)
        cout << lstVert[i] << " ";
    cout << "\n\n"; */
    
    for (j = 1; j < lstLim; j++) {
        chm[ lstVert[j] ][ lstVert[j -1] ] = chm[ lstVert[j -1] ][ lstVert[j] ] = matrizAdj[ lstVert[j -1] ][ lstVert[j] ];
        custo += chm[ lstVert[j] ][ lstVert[j -1] ];
    }
    
    //cout << "Custo: " << custo << ". " << endl;
    
    delete [] lstVert;
    return alg[2] = chm;
}

int *Grafo::DFS(int **_matriz) {
    int *lstVertVisit = new int[2 *numVert -1],
        lstLim = 0;
    DFS(_matriz, lstVertVisit, lstLim, 0);
    
    return lstVertVisit;
}
void Grafo::DFS(int **_matriz, int *lstVertVisit, int &lstLim, int vertAtual) {
    lstVertVisit[lstLim++] = vertAtual;
    
    for (int i = 0; i < numVert; i++)
        if (_matriz[vertAtual][i] != 0) {
            int k = 0;
            bool foiVisitado = false;
            while (k < lstLim && foiVisitado == false)
                if (i == lstVertVisit[k++])
                    foiVisitado = true;
        
            if (foiVisitado == false) {
                DFS(_matriz, lstVertVisit, lstLim, i);
                lstVertVisit[lstLim++] = vertAtual;
            }
        }
}