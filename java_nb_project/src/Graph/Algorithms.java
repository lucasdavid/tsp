package Graph;

/**
 *
 * @author lucasdavid
 */
public class Algorithms {

    public int[] Prim(double[][] _m) {
        int parent[], frj[];
        double cost[], mincost;

        cost = new double[_m.length];
        frj = new int[_m.length];
        parent = new int[_m.length];

        int w, v0;
        v0 = 0;

        for (w = 1; w < _m.length; w++) {
            parent[w] = -1;
            frj[w] = 0;
            cost[w] = _m[0][w];
        }
        parent[0] = 0;

        while (true) {
            mincost = -1;
            for (w = 0; w < _m.length; w++) {
                if (parent[w] == -1 && (mincost > cost[w] || mincost == -1)) {
                    mincost = cost[v0 = w];
                }
            }

            if (mincost == -1) {
                break;
            }

            parent[v0] = frj[v0];
            for (w = 0; w < _m.length; w++) {
                if (parent[w] == -1 && cost[w] > _m[v0][w]) {
                    cost[w] = _m[v0][w];
                    frj[w] = v0;
                }
            }
        }

        return parent;
    }

    public int[] Dijkstra(double[][] _m, int _root) {
        double mincost, cost[] = new double[_m.length];
        int frj[], parent[], w0;

        frj = new int[_m.length];
        parent = new int[_m.length];

        for (int w = 0; w < _m.length; w++) {
            parent[w] = -1;
            cost[w] = _m[_root][w];
            frj[w] = _root;
        }

        parent[_root] = _root;
        cost[_root] = 0;
        w0 = -1;

        while (true) {
            mincost = -1;

            for (int w = 0; w < _m.length; w++) {
                if (parent[w] != -1 || cost[w] == 0) {
                    continue;
                }

                if (mincost > cost[w] || mincost == -1) {
                    mincost = cost[w0 = w];
                }
            }

            if (mincost == -1) {
                break;
            }

            parent[w0] = frj[w0];
            for (int w = 0; w < _m.length; w++) {
                if (cost[w] > cost[w0] + _m[w0][w]) {
                    cost[w] = cost[w0] + _m[w0][w];
                    frj[w] = w0;
                }
            }
        }

        return parent;
    }

    public int[] DFS(int[] _mst, int _root) {
        int nodeList[], lstLim;
        lstLim = 0;

        nodeList = new int[2 * _mst.length - 1];

        innerDFS(_mst, nodeList, lstLim, _root);
        return nodeList;
    }

    private int innerDFS(int[] _mst, int[] _nodeLst, int _i, int _currentNode) {
        _nodeLst[_i++] = _currentNode;

        for (int i = 0; i < _mst.length; i++) {
            if (i != _currentNode && _mst[i] == _currentNode) {
                _i = innerDFS(_mst, _nodeLst, _i, i);
                _nodeLst[_i++] = _currentNode;
            }
        }

        return _i; // return current valid position
    }

    public double TwiceAround(double[][] _m) {

        int _mst[] = Prim(_m);
        int lstVert[] = DFS(_mst, 0);
        int lstLim = 2 * _m.length - 1;

        int i = 0;
        while (i < lstLim - 1) {
            int j = i + 1;

            while (j < lstLim - 1) {
                if (lstVert[j] != lstVert[i]) {
                    j++;
                } else {
                    for (int k = j; k < lstLim - 1; k++) {
                        lstVert[k] = lstVert[k + 1];
                    }
                    lstLim--;
                }
            }
            i++;
        }

        double cost = 0;
        for (i = 1; i < lstLim; i++) {
            cost += _m[ lstVert[i - 1]][ lstVert[i]];
        }

        return cost;
    }

    public double TwiceAroundWDijkstra(double[][] _m) {
        int _mst[], lstVert[], // lista de vertices visitados pelo DFS
                lstLim = 2 * _m.length - 1, // seq. de vertices do CHM
                cost = 0,
                i, j;

        _mst = Dijkstra(_m, 0);
        lstVert = DFS(_mst, 0);

        i = 0;
        while (i < lstLim - 1) {
            j = i + 1;

            while (j < lstLim - 1) {
                if (lstVert[j] != lstVert[i]) {
                    j++;
                } else {
                    for (int k = j; k < lstLim - 1; k++) {
                        lstVert[k] = lstVert[k + 1];
                    }
                    lstLim--;
                }
            }
            i++;
        }

        for (i = 1; i < lstLim; i++) {
            cost += _m[ lstVert[i - 1]][ lstVert[i]];
        }

        return cost;
    }

    public double EdgeScore(double[][] _m) {
        int visitedEdges[][] = new int[_m.length][_m.length];
        boolean lstVertInseridos[] = new boolean[_m.length];
        int numVertInseridos = 1;
        int vAtual = 0;
        int vAlcancado;
        int cost = 0;
        
        for (int i = 0; i < _m.length; i++) {
            for (int j = 0; j < _m.length; j++) {
                visitedEdges[i][j] = 0;
            }
        }

        for (int k = 0; k < _m.length; k++) {
            int spt[] = Dijkstra(_m, k);

            for (int i = 0; i < _m.length; i++) {
                visitedEdges[i][ spt[i]]++;
            }
        }

        for (int i = 1; i < _m.length; i++) {
            lstVertInseridos[i] = false;
        }
        lstVertInseridos[vAtual] = true;

        while (numVertInseridos < _m.length) {
            vAlcancado = vAtual;

            for (int i = 0; i < _m.length; i++) {
                if (lstVertInseridos[i]) {
                    continue; // O vertice I ja esta no circuito.
                }
                // O caminho vAtual-i esta em mais ACMs que vAtual-vAlcancado
                if (visitedEdges[vAtual][i] > visitedEdges[vAtual][vAlcancado]) {
                    vAlcancado = i;
                } // Ambos estao no mesmo numero de ACMs. Escolhe a de menor cost.
                else if (visitedEdges[vAtual][i] == visitedEdges[vAtual][vAlcancado]
                        && _m[vAtual][i] <= _m[vAtual][vAlcancado]) {
                    vAlcancado = i;
                }
            }

            numVertInseridos++;
            lstVertInseridos[vAlcancado] = true;
            cost += _m[vAtual][vAlcancado];
            vAtual = vAlcancado;
        }

        cost += _m[vAtual][0];
        return cost;
    }
}
