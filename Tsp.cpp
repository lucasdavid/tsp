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
#include <time.h>

using std::cout;
using std::cin;
using std::endl;
using std::string;
using std::ifstream;
using std::istringstream;
using std::stringstream;

Tsp::Tsp() {
    srand(time(0));
}
Tsp::Tsp(const char _arq[]) {
    lerGrDoArq(_arq);
}
Tsp::Tsp(const int tNumVert) {
    g.gerarMatRand(tNumVert);    
}

/**
 * 
 * @param _arq: nome do arquivo, que contem a relacao de vertices do grafo, a ser aberto.
 * @return verdadeiro, caso a matriz tenha sido carregada com sucesso. Falso caso contrario.
 */
bool Tsp::lerGrDoArq(const char _arq[]) {
    string linha, elem;
    ifstream inst_tsp(_arq);

    int **tMat,
        tNumVert,
        i, j, k = 0,
        estadoAtual = 0;
    
    double *lstVert[2];
    bool ret;
    
    if (!inst_tsp.is_open()) {
        cout << "Arquivo inacessivel ou nao existente.";
        return false;
    }
    
    while (inst_tsp.good() && !estadoAtual < 3) {
        getline(inst_tsp, linha);
        
        // leitura do cabecalho.
        if (estadoAtual == 0) {
            cout << linha << endl;
            if (linha.substr(0, 9) == "DIMENSION") {
                linha = linha.substr(11, linha.length() -11);
                
                stringstream _sNumVert(linha);
                if (!(_sNumVert >> tNumVert)) {
                    cout << "Erro de conversao!";
                    return false;
                }
                
                lstVert[0] = new double[tNumVert];
                lstVert[1] = new double[tNumVert];
                
                // criando matriz de adj.
                tMat = new int*[tNumVert];
                for (i = 0; i < tNumVert; i++) {
                    tMat[i] = new int[tNumVert];
                    for (j = 0; j < tNumVert; j++)
                        tMat[i][j] = 0;
                } i = j = 0;
            }
            else if (linha == "EDGE_WEIGHT_SECTION")
                estadoAtual = 1;
            else if (linha == "DISPLAY_DATA_SECTION" || linha == "NODE_COORD_SECTION")
                estadoAtual = 2;
        }
        // arq. codif. no formato EDGE_WEIGHT_SECTION.
        // Enquanto existem vertices, decodifica suas distancias
        else if (estadoAtual == 1) {
            if (i >= tNumVert -1) { // todos os vertices ja foram analisados
                estadoAtual = 3;
                continue;
            }
            
            istringstream iss(linha);
            
            j = i +1;
            while (iss && j < tNumVert) {
                iss >> elem;
                stringstream _sElem(elem);
                
                if(!(_sElem >> tMat[i][j])) {
                    cout << "ERRO DE CONVERSAO!" << endl;
                    return false;
                }
                
                tMat[j][i] = tMat[i][j];
                j++;
            }
            i++;
        }
        else if (estadoAtual == 2) {
            if (k >= tNumVert) { // todos os vertices ja foram analisados
                estadoAtual = 4;
                continue;
            }
            
            istringstream iss(linha);
            iss >> elem; // discarta indexador do vertice
            
            iss >> elem;
            stringstream _sX(elem);
            iss >> elem;
            stringstream _sY(elem);

            if(!(_sX >> lstVert[0][k]) || !(_sY >> lstVert[1][k])) {
                cout << "ERRO DE CONVERSAO!" << endl;
                return false;
            }
            k++;
        }
    }
    inst_tsp.close();

    /* Os dados foram recebidos de NODE_COORD_SECTION. Acha a distância euclidiana entre os vertices pelas
     * coordenadas. Estas mesmas sao calculadas pela da equacao de Pitagoras: x**2 +y**2 = z**2
     * Onde:
     * x = pos X da cidade A - pos X da cidade B
     * x = pos Y da cidade A - pos Y da cidade B
     * z = distancia entre as cidades A e B.
     */
    if (estadoAtual == 4)
        for (i = 0; i < tNumVert; i++)
            for (j = i +1; j < tNumVert; j++) { // distancia euclidiana
                double dx = pow(lstVert[0][i] -lstVert[0][j], 2);
                double dy = pow(lstVert[1][i] -lstVert[1][j], 2);

                tMat[j][i] = tMat[i][j] = sqrt(dx +dy);
            }
    
    delete [] lstVert[0];
    delete [] lstVert[1];

    return ret = g.carregarMatExist(tMat, tNumVert);
}
bool Tsp::gerarGrRand(int tNumVert) {
    return g.gerarMatRand(tNumVert); // gera matri de adj. randomica
}

/**
 * Invoca o algoritmo twiceAround para resolver o TSP e exibe a resposta.
 */
int Tsp::resTwiAroundOrig() {
    int custCirc = -1;
    if (g.getValido() == false)
        cout << "Grafo invalido, nao ha nada para resolver." << endl;
    else {
        custCirc = g.twiceAround();
        //g.exibirMat(2); // exibir circuito hamiltoniano
    }
    
    return custCirc;
}
/**
 * Invoca o algoritmo twiceAround para resolver o TSP e exibe a resposta.
 * Este algoritmo utilizara o algoritmo de dijkstra como entrada.
 */
int Tsp::resTwiAroundDijk() {
    int custCirc = -1;
    if (g.getValido() == false)
        cout << "Grafo invalido, nao ha nada para resolver." << endl;
    else
        custCirc = g.twiceAroundComDijkstra();
    
    return custCirc;
}
/**
 * Invoca o algoritmo de ocorrencia em Shortest-path trees para resolver o TSP
 * e exibe a resposta.
 */
int Tsp::resOcorrEmSPT() {
    int tCust = -1;
    if (g.getValido() == false)
        cout << "Grafo invalido, nao ha nada para resolver." << endl;
    else
        tCust = g.ocorrEmSPT();
    
    return tCust;
}

/**
 * Compara os algoritmos Twice-around original ao modificado (que utiliza a arvores de caminhos minimos 
 * como entrada) @_numInter vezes, em relacao ao custo do circuito obtido (quando menor, melhor).
 * @param _numInter numero de vezes que os circuitos sao comparados.
 * @param _intervTamMat intervalo do tamanho que uma matriz de adj. criada pode assumir.
 */
void Tsp::cmpTwiAround(const int _numInter, const int _intervTamMat, const bool _exibirMat, const bool _exibirCustos) {
    int tCustTwiAround, tCustOcorrSPT, tContTwiAround, tContOcorrSPT, tTam;
    
    tContTwiAround = tContOcorrSPT = 0;
    
    if (_numInter < 1 || _intervTamMat < 1)
        cout << "Erro: numero de interacoes invalido." << endl;
    else {
        for (int i = 0; i < _numInter; i++) {
            tTam = 0; // acha um tamanho randomico para a matriz de adj.
            while (tTam == 0) tTam = rand() %_intervTamMat;
            
            gerarGrRand(tTam);
            if (_exibirMat == true)
                g.exibirMat();

            tCustTwiAround = resTwiAroundOrig(); // calc. com o custo pelo twice-around original
            tCustOcorrSPT = resTwiAroundDijk(); // calc. com o custo pelo twice-around modificado
            
            if (_exibirCustos == true) {
                cout << "Custo (tw-ar original): " << tCustTwiAround << ".\n"
                     << "Custo (tw-ar dijkstra): " << tCustOcorrSPT << ".\n\n";
            }
            
            if (tCustTwiAround < tCustOcorrSPT)
                tContTwiAround++;
            else if (tCustTwiAround > tCustOcorrSPT)
                tContOcorrSPT++;
        }
        
        cout << "N. de vezes que o alg orig foi melhor: " << tContTwiAround << endl
             << "N. de vezes que o alg dijk foi melhor: " << tContOcorrSPT << endl;
    }
}

/**
 * Compara os algoritmos Twice-around original ao metodo de ocorrencia em shortest-path trees
 * @_numInter vezes, em relacao ao custo do circuito obtido (quando menor, melhor).
 * @param _numInter numero de vezes que os circuitos sao comparados.
 * @param _intervTamMat intervalo do tamanho que uma matriz de adj. criada pode assumir.
 */
void Tsp::cmpTwiAroundEOcorrSPT(const int _numInter, const int _intervTamMat, const bool _exibirMat, const bool _exibirCustos) {
    
    int tCustTwiAround,
        tCustOcorrSPT,
        tContTwiAround,
        tContOcorrSPT,
        tTam;
    
    tContTwiAround = tContOcorrSPT = 0;
    
    if (_numInter < 1 || _intervTamMat < 1)
        cout << "Erro: numero de interacoes invalido." << endl;
    else {
        for (int i = 0; i < _numInter; i++) {
            tTam = 0; // acha um tamanho randomico para a matriz de adj.
            while (tTam <= 1)
                tTam = rand() %_intervTamMat;
            
            gerarGrRand(tTam);
            if (_exibirMat) g.exibirMat();

            tCustTwiAround = resTwiAroundOrig(); // calc. com o custo pelo twice-around original
            tCustOcorrSPT = resOcorrEmSPT(); // calc. com o custo pelo metodo de ocorrencia em shortest-path trees
            
            if (_exibirCustos)
                cout << "Custo (tw-ar original): " << tCustTwiAround << ".\n"
                     << "Custo (shortest-path): " << tCustOcorrSPT << ".\n\n";
            
            if (tCustTwiAround < tCustOcorrSPT)
                tContTwiAround++;
            else if (tCustTwiAround > tCustOcorrSPT)
                tContOcorrSPT++;
        }
        
        cout << "N. de interacoes: " << _numInter << endl 
             << "Intervalo de valores possiveis: (2, " << _intervTamMat << ")" << endl
             << "N. de vezes que o alg orig foi melhor: " << tContTwiAround << endl
             << "N. de vezes que o shortest-path foi melhor: " << tContOcorrSPT << endl;
    }
}

