/* 
 * File:   Tsp.h
 * Author: david
 *
 * Created on February 8, 2013, 1:40 PM
 */

#ifndef TSP_H
#define	TSP_H

#include "Grafo.h"

class Tsp {
public:
    Tsp();
    Tsp(char []);
    
    void resolver();
    bool lerGrDoArq(const char []);
    bool gerarGrRand(int = 4);
private:
    Grafo g;

};

#endif	/* TSP_H */

