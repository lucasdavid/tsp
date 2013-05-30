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
        w0 = 0;

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

    private int innerDFS(int[] _mst, int[] _nodeLst, int _i, int _current) {
        _nodeLst[_i++] = _current;

        for (int i = 0; i < _mst.length; i++) {
            if (i != _current && _mst[i] == _current) {
                _i = innerDFS(_mst, _nodeLst, _i, i);
                _nodeLst[_i++] = _current;
            }
        }

        return _i; // return current valid position
    }

    public double NearestNeighbor(double[][] _m) {
        double cost = 0;
        int current;

        int nodes[] = new int[_m.length];
        int nodesLength = _m.length - 1;

        for (int i = 0; i < nodes.length - 1; i++) {
            nodes[i] = i + 1;
        }
        current = nodes[nodes.length - 1] = 0;

        while (nodesLength > 0) {
            int nearest = nodes[0];
            for (int i = 1; i < nodesLength; i++) {
                if (_m[current][nearest] > _m[current][nodes[i]]) {
                    nearest = nodes[i];
                }
            }
            
            cost += _m[current][nearest];
            nodes[0] = nodes[--nodesLength];
            current = nearest;
        }

        cost += _m[current][0];
        return cost;
    }

    public double TwiceAround(double[][] _m) {

        int _mst[] = Prim(_m);
        int visitedNodes[] = DFS(_mst, 0);
        int lstLim = 2 * _m.length - 1;

        int i = 0;
        while (i < lstLim - 1) {
            int j = i + 1;

            while (j < lstLim - 1) {
                if (visitedNodes[j] != visitedNodes[i]) {
                    j++;
                } else {
                    for (int k = j; k < lstLim - 1; k++) {
                        visitedNodes[k] = visitedNodes[k + 1];
                    }
                    lstLim--;
                }
            }
            i++;
        }

        double cost = 0;
        for (i = 1; i < lstLim; i++) {
            cost += _m[ visitedNodes[i - 1]][ visitedNodes[i]];
        }

        return cost;
    }

    public double TwiceAroundWDijkstra(double[][] _m) {

        int _mst[] = Dijkstra(_m, 0);
        int visitedNodes[] = DFS(_mst, 0);
        int lstLim = 2 * _m.length - 1;

        double cost = 0;

        int i = 0;
        while (i < visitedNodes.length - 1) {
            int j = i + 1;

            while (j < lstLim - 1) {
                if (visitedNodes[j] != visitedNodes[i]) {
                    j++;
                } else {
                    for (int k = j; k < lstLim - 1; k++) {
                        visitedNodes[k] = visitedNodes[k + 1];
                    }
                    lstLim--;
                }
            }
            i++;
        }

        for (i = 1; i < lstLim; i++) {
            cost += _m[visitedNodes[i - 1]][visitedNodes[i]];
        }

        return cost;
    }

    public double EdgeScore(double[][] _m) {
        int edgeScore[][] = new int[_m.length][_m.length];
        boolean reacheds[] = new boolean[_m.length];
        int insertedNodes = 1;
        int current = 0;

        for (int i = 0; i < _m.length; i++) {
            for (int j = 0; j < _m.length; j++) {
                edgeScore[i][j] = 0;
            }
        }

        for (int k = 0; k < _m.length; k++) {
            int spt[] = Dijkstra(_m, k);

            for (int i = 0; i < k; i++) {
                edgeScore[i][spt[i]]++;
            }
            for (int i = k + 1; i < _m.length; i++) {
                edgeScore[i][spt[i]]++;
            }
        }

        for (int i = 1; i < _m.length; i++) {
            reacheds[i] = false;
        }
        reacheds[current] = true;

        int reached;
        double cost = 0;
        while (insertedNodes < _m.length) {
            reached = current;

            for (int i = 0; i < _m.length; i++) {
                if (reacheds[i] == true) {
                    continue;
                }
                if (edgeScore[current][i] > edgeScore[current][reached]) {
                    reached = i;
                } else if (edgeScore[current][i] == edgeScore[current][reached]
                        && _m[current][i] <= _m[current][reached]) {
                    reached = i;
                }
            }

            insertedNodes++;
            reacheds[reached] = true;
            cost += _m[current][reached];
            current = reached;
        }

        cost += _m[current][0];
        return cost;
    }
}
