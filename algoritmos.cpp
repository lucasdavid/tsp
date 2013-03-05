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
 * superior para o peso das aresCaminhadas.
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
    
    return mst; // retorna a MST
}
/**
 * Referencia: este algoritmo foi retirado do material de Paulo Feofiloff - IME-USP.
 * Novamente, -1 foi utilizado como representante de infinito.
 * 
 * http://www.ime.usp.br/~pf/algoritmos_para_grafos/aulas/dijkstra.html
 */
int **Grafo::dijkstra(const int _vertInicial, int **_acm) {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false) return NULL;
    
    // acm: matriz de adjacencia de uma arvore de caminhos minimos
    int **acm = _acm == NULL
              ? new int*[numVert]
              : _acm,
                      
        *cost   = new int[numVert], 
        *frj    = new int[numVert],
        *parent = new int[numVert],
        w, w0;

    for (int i = 0; i < numVert; i++) {
        if (_acm == NULL)
            acm[i] = new int[numVert];
            
        for (int j = 0; j < numVert; j++)
            acm[i][j] = 0;
    }

    for (w = 0; w < numVert; w++) {
        parent[w] = -1;
        cost[w] = matrizAdj[_vertInicial][w];
        frj[w] = _vertInicial;
    }
    
    parent[_vertInicial] = _vertInicial;
    cost[_vertInicial] = 0;
    
    while (1) {
        int mincost = -1;
        for (w = 0; w < numVert; w++) {
            if (parent[w] != -1 || cost[w] == 0)
                continue; // vert ja alcancado ou nao possui aresta com vert atual.
            
            // se menor que mincost ou mincost == infinito
            if (mincost > cost[w] || mincost == -1)
                mincost = cost[w0 = w];
        }
        if (mincost == -1) break;
        parent[w0] = frj[w0];
        for (w = 0; w < numVert; w++)
            if (cost[w] > cost[w0] + matrizAdj[w0][w]) {
                cost[w] = cost[w0] + matrizAdj[w0][w];
                frj[w] = w0;
            }
    }
    
    for (w = 0; w < numVert; w++)
        if (parent[w] != -1 && cost[w] != 0)
            acm[ w ][ parent[w] ] = acm[ parent[w] ][ w ] = matrizAdj[ w ][ parent[w] ];
    
    delete [] frj;
    delete [] cost;
    delete [] parent;
    
    return acm;
}
int *Grafo::DFS(int **_matriz) {
    int *lstVertVisit = new int[2 * numVert - 1],
            lstLim = 0;
    DFS(_matriz, lstVertVisit, lstLim, 0);

    return lstVertVisit;
}
void Grafo::DFS(int **_matriz, int *lstVertVisit, int &lstLim, int vAtual) {
    lstVertVisit[lstLim++] = vAtual;

    for (int i = 0; i < numVert; i++)
        if (_matriz[vAtual][i] != 0) {
            int k = 0;
            bool foiVisitado = false;
            while (k < lstLim && foiVisitado == false)
                if (i == lstVertVisit[k++])
                    foiVisitado = true;

            if (foiVisitado == false) {
                DFS(_matriz, lstVertVisit, lstLim, i);
                lstVertVisit[lstLim++] = vAtual;
            }
        }
}

int Grafo::twiceAround() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false)
        return -1;
    
    int **mst, // arvore spanning minima
        *lstVert, // lista de vertices visitados pelo DFS
        lstLim = 2 * numVert - 1, // seq. de vertices do CHM
        custo = 0,
        i, j;
        
    mst     = prim();
    lstVert = DFS(mst);

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

    /*cout << "== pos-enxugamento ==" << endl;
    for (i = 0; i < lstLim; i++)
        cout << lstVert[i] << " ";
    cout << "\n\n"; */
    
    for (i = 1; i < lstLim; i++)
        custo += matrizAdj[ lstVert[i -1] ][ lstVert[i] ];

    delMatriz(mst);
    delete [] lstVert;
                
    return custo;
}
int Grafo::twiceAroundComDijkstra() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false)
        return -1;
    
    int **spt, // shortest path tree
        *lstVert, // lista de vertices visitados pelo DFS
        lstLim = 2 * numVert - 1, // seq. de vertices do CHM
        custo = 0,
        i, j;
        
    spt     = dijkstra();
    lstVert = DFS(spt);

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

    /*cout << "== pos-enxugamento ==" << endl;
    for (i = 0; i < lstLim; i++)
        cout << lstVert[i] << " ";
    cout << "\n\n"; */

    for (i = 1; i < lstLim; i++)
        custo += matrizAdj[ lstVert[i -1] ][ lstVert[i] ];
    
    delMatriz(spt);
    delete [] lstVert;
    
    return custo;
}
int Grafo::ocorrEmSPT() {
    // o grafo atual nao e valido. Nada a fazer.
    if (valido == false) return -1;
    
    int **tAcm           = new int*[numVert],
        **aresCaminhadas = new int*[numVert],
        i, j, v0,
        iVertInser,
        
        vAlcancado = 0,
        vAtual = 0,
        custo = 0;
    
    bool *lstVert = new bool[numVert];
    
    for (i = 0; i < numVert; i++) {
        tAcm[i]           = new int[numVert];
        aresCaminhadas[i] = new int[numVert];
        
        lstVert[i] = false;
    }
    
    iVertInser = numVert;
    
    while (iVertInser > 1) {
        for (i = 0; i < numVert; i++)
            for (j = 0; j < numVert; j++)
                aresCaminhadas[i][j] = 0;
        
        for (v0 = 0; v0 < numVert; v0++) {
            if (lstVert[v0] == true)
                continue;
            
            tAcm = dijkstra(v0, tAcm);
            
            for (i = 0; i < numVert; i++)
                for (j = 0; j < numVert; j++)
                    if (tAcm[i][j] != 0)
                        aresCaminhadas[i][j]++;
        }
        
        vAlcancado = vAtual;
        for (v0 = 0; v0 < numVert; v0++) {
            if (lstVert[v0] == true)
                continue;
            
            if (aresCaminhadas[vAtual][v0] > aresCaminhadas[vAtual][vAlcancado])
                vAlcancado = v0;
            
            else if (aresCaminhadas[vAtual][v0] == aresCaminhadas[vAtual][vAlcancado]
                && matrizAdj[vAtual][v0] < matrizAdj[vAtual][vAlcancado])
                vAlcancado = v0;
        }
        
        custo += matrizAdj[vAtual][vAlcancado];
        
        // elimina o vertice de origem
        for (i = 0; i < numVert; i++)
            tAcm[vAtual][i] = tAcm[vAtual][i] = 0;        
        lstVert[vAtual] = true;
        vAtual = vAlcancado;
        
        iVertInser--;
    }
    
    custo += matrizAdj[vAlcancado][0];
    
    for (i = 0; i < numVert; i++)
        delete [] aresCaminhadas[i];
    delete [] aresCaminhadas;
    delete [] lstVert;
    delMatriz(tAcm);
    
    return custo;
}