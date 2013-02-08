/* 
 * File:   Tsp.cpp
 * Author: david
 * 
 * Created on February 8, 2013, 1:40 PM
 */

#include "Tsp.h"

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <stdlib.h>
#include <math.h>

using namespace std;

Tsp::Tsp() {

}
Tsp::Tsp(char _arq[]) {
    lerGrDoArq(_arq);
}

/**
 * 
 * @param _arq: nome do arquivo, que contem a relacao de vertices do grafo, a ser aberto.
 * @return verdadeiro, caso a matriz tenha sido carregada com sucesso. Falso caso contrario.
 */
bool Tsp::lerGrDoArq(const char _arq[]) {
    string linha;
    ifstream inst_tsp(_arq);

    int lstVert[52][2], **tMat;
    int tamLstVert = 52,
            i, j, k = 0;
    
    bool ret;

    if (inst_tsp.is_open() == false) {
        cout << "Arquivo inacessivel ou nao existente.";
        return false;
    } else {
        while (inst_tsp.good()) {
            getline(inst_tsp, linha);

            // nao e um digito, portanto faz parte de um cabecalho.
            if (isdigit(linha[0]) == false) {
                cout << linha << endl;
            } else {
                i = 1;
                while (linha[i] != ' ') i++; // percorre ate o x: |123 x23 123|
                ++i;
                j = 0;
                
                while (linha[i + j] != ' ') j++; // decodifica coordenada X
                stringstream x(linha.substr(i, j - 2));
                
                i = i + j + 1;
                j = 0;
                while (linha[i + j] != '.') j++; // decodifica coordenada Y
                stringstream y(linha.substr(i, j));
                
                if (!(x >> lstVert[k][0]) || !(y >> lstVert[k][1])) {
                    cout << "Erro de conversao!";
                    return false;
                }
                k++;
                
                cout << linha << endl;
            }
        }
        inst_tsp.close();
        
        // criando matriz de adj.
        tMat = new int*[tamLstVert];
        for (i = 0; i < tamLstVert; i++) {
            tMat[i] = new int[tamLstVert];
            for (j = 0; j < tamLstVert; j++)
                tMat[i][j] = 0;
        }
        
        for (i = 0; i < tamLstVert; i++) {
            for (j = i +1; j < tamLstVert; j++) { // distancia euclidiana
                int dx = pow(lstVert[i][0] -lstVert[j][0], 2);
                int dy = pow(lstVert[i][1] -lstVert[j][1], 2);
                
                tMat[j][i] = tMat[i][j] = sqrt(dx +dy);
            }
        }
        
        ret = g.carregarMatExist(tMat, tamLstVert);
        //if (ret == true) g.exibirMat();
        
        return ret;
    }
}
bool Tsp::gerarGrRand(int _numVert) {
    bool ret;

    ret = g.gerarMatRand(_numVert); // gera matri de adj. randomica
    if (ret == true) g.exibirMat(); // exibe grafo gerado.

    return ret;
}

/**
 * Invoca o algoritmo twiceAround para resolver o TSP e exibe a resposta.
 */
void Tsp::resolver() {
    if (g.getValido() == false)
        cout << "Grafo invalido, nao ha nada para resolver." << endl;
    else {
        g.twiceAround();
        g.exibirMat(2); // exibir circuito hamiltoniano
    }
}