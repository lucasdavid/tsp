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
#define NUM_ALG 3

class Grafo {
public:
    Grafo();
    Grafo(int);
    Grafo(const Grafo& orig);
    virtual ~Grafo();
    
    bool gerarMatRand(int);
    bool reiniciar();
    void exibirMat(int = -1);
    
    int **prim();
    int **dijkstra();
    int **twiceAround();
    int *DFS(int **);
    
    int get(char []);
    int buscarVert(int);
    
private:
    bool valido;
    int  numVert;
    int  **matrizAdj;
    int  **alg[NUM_ALG];
    
    void DFS(int **, int *, int &, int);
};

#endif	/* GRAFO_H */

