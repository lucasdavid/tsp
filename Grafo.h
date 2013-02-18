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
#define NUM_ALG 4

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
    void exibirMat(int = -1) const;
    
    int **prim();
    int **dijkstra(const int = 0);
    int twiceAround();
    int twiceAroundComDijkstra();
    int novoMetodoACM();
    
    int *DFS(int **);
    
    bool getValido() const;
    int getNumVert() const;
    int **getMatrizAdj() const;
    int **const*getAlg() const;
    
protected:
    bool valido;
    int  numVert;
    int  **matrizAdj;
    int  **alg[NUM_ALG];
    
    void DFS(int **, int *, int &, int);
};

#endif	/* GRAFO_H */