/* 
 * File:   algoritmos.cpp
 * Author: david
 *
 * Created on February 8, 2013, 00:47 PM
 */

#include "Grafo.h"
#include <iostream>

using std::cout;
using std::cin;
using std::endl;

/**
 * ==== Algoritmos =====
 */
/**
 * Referencia: este algoritmo foi retirado do material de Paulo Feofiloff - IME-USP. Tendo sido realizado uma
 * unica alteracao: utilizar o valor -1, que representa infinito, ao inves de uma variavel pesoMAX como limitante
 * superior para o peso das arestas.
 * 
 * http://www.ime.usp.br/~pf/algoritmos_para_grafos/aulas/prim.html
 */
int **Grafo::prim() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false)
        return NULL;

    // cria matriz de adjacencia da MST
    int **mst = new int*[numVert], i;
    for (i = 0; i < numVert; i++) {
        mst[i] = new int[numVert];
        for (int j = 0; j < numVert; j++)
            mst[i][j] = 0;
    }

    int *cost = new int[numVert], w, v0 = 0, *frj = new int[numVert];
    int *parent = new int[numVert];
    for (w = 1; w < numVert; w++) {
        parent[w] = -1;
        frj[w] = 0;
        cost[w] = matrizAdj[0][w];
    }
    parent[0] = 0;
    while (1) {
        int mincost = -1;
        for (w = 0; w < numVert; w++)
            if (parent[w] == -1 && (mincost > cost[w] || mincost == -1))
                mincost = cost[v0 = w];

        if (mincost == -1)
            break;

        parent[v0] = frj[v0];
        for (w = 0; w < numVert; w++)
            if (parent[w] == -1 && cost[w] > matrizAdj[v0][w]) {
                cost[w] = matrizAdj[v0][w];
                frj[w] = v0;
            }
    }

    for (i = 1; i < numVert; i++)
        mst[ i ][ parent[i] ] = mst[ parent[i] ][ i ] = matrizAdj[ i ][ parent[i] ];

    delete [] frj;
    delete [] cost;
    delete [] parent;

    return alg[0] = mst; // retorna a MST
}
/**
 * Referencia: este algoritmo foi retirado do material de Paulo Feofiloff - IME-USP.
 * Novamente, -1 foi utilizado como representante de infinito.
 * 
 * http://www.ime.usp.br/~pf/algoritmos_para_grafos/aulas/dijkstra.html
 */
int **Grafo::dijkstra() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false)
        return NULL;
    
    int **acm = new int*[numVert]; // acm: matriz de adjacencia de uma arvore de caminhos minimos
    
    int *cost = new int[numVert], 
        *frj = new int[numVert],
        *parent = new int[numVert],
        w, w0;
    
    for (int i = 0; i < numVert; i++) {
        acm[i] = new int[numVert];
        for (int j = 0; j < numVert; j++)
            acm[i][j] = 0;
    }

    for (w = 0; w < numVert; w++) {
        parent[w] = -1;
        cost[w] = matrizAdj[0][w];
        frj[w] = 0;
    }
    
    parent[0] = 0;
    cost[0] = 0;
    
    while (1) {
        int mincost = -1;
        for (w = 0; w < numVert; w++)
            if (parent[w] == -1 && (mincost > cost[w] || mincost == -1))
                mincost = cost[w0 = w];
        if (mincost == -1) break;
        parent[w0] = frj[w0];
        for (w = 0; w < numVert; w++)
            if (cost[w] > cost[w0] + matrizAdj[w0][w]) {
                cost[w] = cost[w0] + matrizAdj[w0][w];
                frj[w] = w0;
            }
    }
    
    for (int i = 1; i < numVert; i++)
        acm[ i ][ parent[i] ] = acm[ parent[i] ][ i ] = matrizAdj[ i ][ parent[i] ];
    
    delete [] frj;
    delete [] cost;
    delete [] parent;

    return alg[1] = acm;
}
int Grafo::twiceAround() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false)
        return -1;
    
    int **mst, // arvore spanning minima
        *lstVert, // lista de vertices visitados pelo DFS
        lstLim = 2 * numVert - 1,
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
    while (i < lstLim - 1) {
        int j = i + 1;

        while (j < lstLim - 1)
            if (lstVert[j] != lstVert[i])
                j++;
            else {
                for (int k = j; k < lstLim - 1; k++)
                    lstVert[k] = lstVert[k + 1];
                lstLim--;
            }
        i++;
    }

    /*cout << "== Enxugamento ==" << endl;
    for (i = 0; i < lstLim; i++)
        cout << lstVert[i] << " ";
    cout << "\n\n"; */

    for (j = 1; j < lstLim; j++) {
        chm[ lstVert[j] ][ lstVert[j - 1] ] = chm[ lstVert[j - 1] ][ lstVert[j] ] = matrizAdj[ lstVert[j - 1] ][ lstVert[j] ];
        custo += chm[ lstVert[j] ][ lstVert[j - 1] ];
    }

    delete [] lstVert;
    if (alg[2] != NULL) {
        for (i = 0; i < numVert; i++)
            delete [] alg[2][i];
        
        delete [] alg[2];
        alg[2] = NULL;
    }
    alg[2] = chm;
    
    return custo;
}
int Grafo::twiceAroundComDijkstra() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false)
        return -1;
    
    int **mst, // arvore spanning minima
        *lstVert, // lista de vertices visitados pelo DFS
        lstLim = 2 * numVert - 1,
        **chm = new int*[numVert], // matriz adj. do circuito hamiltoniano
        i, j;
        
    int custo = 0;

    mst = dijkstra();
    
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
    while (i < lstLim - 1) {
        int j = i + 1;

        while (j < lstLim - 1)
            if (lstVert[j] != lstVert[i])
                j++;
            else {
                for (int k = j; k < lstLim - 1; k++)
                    lstVert[k] = lstVert[k + 1];
                lstLim--;
            }
        i++;
    }

    /*cout << "== Enxugamento ==" << endl;
    for (i = 0; i < lstLim; i++)
        cout << lstVert[i] << " ";
    cout << "\n\n"; */

    for (j = 1; j < lstLim; j++) {
        chm[ lstVert[j] ][ lstVert[j - 1] ] = chm[ lstVert[j - 1] ][ lstVert[j] ] = matrizAdj[ lstVert[j - 1] ][ lstVert[j] ];
        custo += chm[ lstVert[j] ][ lstVert[j - 1] ];
    }

    delete [] lstVert;
    
    if (alg[2] != NULL) {
        for (i = 0; i < numVert; i++)
            delete [] alg[2][i];
        
        delete [] alg[2];
        alg[2] = NULL;
    }
    alg[2] = chm;
    
    return custo;
}
int *Grafo::DFS(int **_matriz) {
    int *lstVertVisit = new int[2 * numVert - 1],
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