#TSP#

*Criação*: 07/02/2013

*Última modificação*: 27/05/2013

##Autor##

Lucas Oliveira David.

Universidade Federal de São Carlos - São Carlos, SP, Brasil.

##Introdução##

>Given a list of cities and the distances between each pair of cities, what is the shortest possible route that visits each city exactly once and returns to the origin city? It is an NP-hard problem in combinatorial optimization, important in operations research and theoretical computer science.
**(Wikipedia)**

##Breve descrição##

Explorando caminhos ótimos e comunidades no problema do caixeiro viajante.

##Descrição detalhada##

Manipulação de algoritmos a fim da aproximação do TSP (problema do caixeiro viajante). Conceitos teóricos, como árvores de caminhos mínimos, árvores varredoras mínimas, comunidades ou algoritmos já implementados na linguagem em questão (C++) foram amplamente utilizados durante o trabalho. Seus respectivos autores serão citados, concomitantemente.

##Composição do repositório##
###Projeto C++###
* Implementação do ambiente de análise e comparação de resultados
* Implementação da leitura de instâncias do TSP a partir de um banco previamente definido: <http://www.tsp.gatech.edu/data/>
* Geração de grafos k-n, para k > 3, com arestas de pesos aleatórias
* Algoritmo de Prim
* Algoritmo de Dijkstra
* Algoritmo DFS - Busca em profundidade
* Algoritmo Twice-around
* Algoritmo Twice-around (dijkstra)
* Algoritmo EdgeScore simplificado
* Algoritmo EdgeScore

###Projeto Java###
* Implementação do ambiente de análise e comparação de resultados
* Implementação da leitura de instâncias do TSP a partir de um banco previamente definido: <http://www.tsp.gatech.edu/data/>
* Geração de grafos k-n, para k > 3, com arestas de pesos aleatórias
* Algoritmo de Prim
* Algoritmo de Dijkstra
* Algoritmo DFS - Busca em profundidade
* Algoritmo Twice-around
* Algoritmo Twice-around (dijkstra)

#Referências#

**Paulo Feofiloff - IME-USP.**
<http://www.ime.usp.br/~pf/algoritmos_para_grafos/aulas/prim.html>
<http://www.ime.usp.br/~pf/algoritmos_para_grafos/aulas/dijkstra.html>

**Newman, M. E. J.; Girvan, M. Título: Finding and evaluating community structure in networks.**
<https://docs.google.com/file/d/0Bxl_AQ6nM3yXMENIUDk3aHhDazA/edit?usp=sharing>
