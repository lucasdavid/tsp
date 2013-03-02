/* 
/* 
 * File:   Grafo.cpp
 * Author: david
 * 
 * Created on February 5, 2013, 11:08 PM
 */

#include "Grafo.h"

#include <cstdlib>
#include <iostream>

using std::cout;
using std::cin;
using std::endl;

Grafo::Grafo() {
    valido = false;
    matrizAdj = NULL;
}
Grafo::Grafo(int _numVert) {
    valido = false;
    matrizAdj = NULL;

    gerarMatRand(_numVert);
}
Grafo::Grafo(int** _mat, int _tam) {
    valido = false;
    matrizAdj = NULL;
    numVert = _tam;
    
    carregarMatExist(_mat, _tam);
}
Grafo::Grafo(const Grafo& _orig) {
    int **tMatrizAdj = _orig.getMatrizAdj();
    int tNumVert     = _orig.getNumVert(),
        tValido      = _orig.getValido();

    matrizAdj = NULL;
    numVert = tNumVert;
    valido = tValido;
    
    if (valido) {
        matrizAdj = new int*[numVert];
        for (int i = 0; i < numVert; i++) {
            matrizAdj[i] = new int[numVert];
            for (int j = 0; j < numVert; j++)
                matrizAdj[i][j] = tMatrizAdj[i][j];
        }
    }
}
Grafo::~Grafo() {
    reiniciar();
}

/**
 * Desaloca a memoria utilizada pela matriz de adjacencia do grafo e dos algoritmos aplicados a este.
 * 
 * @return true, caso o grafo tenha sido reiniciado com sucesso. Falso caso ele nao fosse um grafo
 * valido para ser reiniciado.
 */
bool Grafo::reiniciar() {
    if (valido == false)
        return false; // o grafo nao foi inicializado.
    else {
        // rm matriz de adj do grafo original.
        for (int i = 0; i < numVert; i++)
            delete [] matrizAdj[i];
        delete [] matrizAdj;
        matrizAdj = NULL;

        valido = false;
        return true;
    }
}
/**
 * Carrega matriz ja existente.
 * @param _mat matriz de adjacencia a ser carregada
 * @param _tam tamanho ij da matriz @_mat
 * @return false, caso a matriz seja invalida
 */
bool Grafo::carregarMatExist(int **_mat, int _tam) {
    reiniciar();
        
    if (_tam < 1 || _mat == NULL)
        return false;
    else {
        matrizAdj = _mat;
        numVert   = _tam;
        return valido = true;
    }
}
/**
 * gera uma matriz de adjacencia randomica, com um numero de vertices igual ao valor de @_numVert.
 * @param:
 *     int @_numVert, numero de vertices que o grafo devera conter. Se esse valor for inferior a 1, o grafo
 *     nao sera criado.
 * 
 * @return: true, se o grafo foi criado. Falso caso contrario.
 */
bool Grafo::gerarMatRand(int _numVert) {
    // numero de vertices invalido, nada a fazer
    if (_numVert < 1) return false;

    reiniciar(); // ja existe uma matriz alocada. Vamos substitui-la.
    
    matrizAdj = new int *[_numVert];
    for (int i = 0; i < _numVert; i++) {
        matrizAdj[i] = new int[_numVert];
        for (int j = 0; j < _numVert; j++)
            matrizAdj[i][j] = 0;
    }
    
    // Preenchimento randomico.
    // Esse procedimento pode demorar exaustivamente
    // para grafos com um grande numero de vertices.
    for (int i = 0; i < _numVert; i++) {
        for (int j = i +1; j < _numVert; j++) {        
            matrizAdj[i][j] = matrizAdj[j][i] = rand() % INTERV_DESVIO;

            if (matrizAdj[i][j] < MIN_AREST)
                matrizAdj[i][j] = matrizAdj[j][i] = MIN_AREST;
        }
    }

    numVert = _numVert;
    return valido = true;
}
/**
 * Exibe a matriz de adjacencia do grafo original, caso nada seja passado
 * como parametro, ou a matriz de algum dos algoritmos aplicados, contida em uma
 * posicao do vetor Grafo.alg[].
 * @param:
 *     int @_mat, um indice valido para o vetor Grafo.alg[] (default = -1).
 * @return: nada.
 */
void Grafo::exibirMat(int **_mat) const {
    if (valido == false)
        cout << "Erro: o objeto nao e um grafo valido." << endl;
    else {
        int **tmpMat = (_mat == 0)
                     ? matrizAdj
                     : _mat;

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
bool Grafo::delArvore(int **_arv) {
    if (_arv == NULL)
        return false;
    else {
        for (int i = 0; i < numVert; i++)
            delete [] _arv[i];
        delete [] _arv;
        
        return true;
    }
}

bool Grafo::getValido() const {
    return valido;
}
int  Grafo::getNumVert() const {
    return numVert;
}
int**Grafo::getMatrizAdj() const {
    return matrizAdj;
}