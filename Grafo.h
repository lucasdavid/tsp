/* 
 * File:   Grafo.h
 * Author: david
 *
 * Created on February 5, 2013, 11:08 PM
 */

#ifndef GRAFO_H
#define	GRAFO_H

#define INTERV_DESVIO 10
#define MIN_AREST 1 // CUIDADO! Mudar isso para 0 tira a garantia que o grafo seja o K-N.

class Grafo {
public:
    Grafo();
    Grafo(int);
    Grafo(int **, int);
    Grafo(const Grafo& _orig);
    virtual ~Grafo();
    
    bool gerarMatRand(int);
    bool carregarMatExist(int **, int);
    bool reiniciar();
    void exibirMat(int ** = 0) const;
    
    int **prim();
    int **dijkstra(const int = 0, int ** = 0);
    int twiceAround();
    int twiceAroundComDijkstra();
    int OcorrEmSPT();
    int *DFS(int **);
    
    bool delMatriz(int **);
    
    bool getValido() const;
    int getNumVert() const;
    int **getMatrizAdj() const;
    
private:
    bool valido;
    int  numVert;
    int  **matrizAdj;
    
    void DFS(int **, int *, int &, int);
};

#endif	/* GRAFO_H */