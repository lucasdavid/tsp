package Graph;

/**
 *
 * @author lucasdavid
 */
public class Algorithms {

    public int[] Prim(int[][] _m, int _nodes) {
        int frj[], parent[];
        double cost[], mincost;

        cost = new double[_nodes];
        frj = new int[_nodes];
        parent = new int[_nodes];

        int w, v0;
        v0 = 0;

        for (w = 1; w < _nodes; w++) {
            parent[w] = -1;
            frj[w] = 0;
            cost[w] = _m[0][w];
        }
        parent[0] = 0;

        while (true) {
            mincost = -1;
            for (w = 0; w < _nodes; w++) {
                if (parent[w] == -1 && (mincost > cost[w] || mincost == -1)) {
                    mincost = cost[v0 = w];
                }
            }

            if (mincost == -1) {
                break;
            }

            parent[v0] = frj[v0];
            for (w = 0; w < _nodes; w++) {
                if (parent[w] == -1 && cost[w] > _m[v0][w]) {
                    cost[w] = _m[v0][w];
                    frj[w] = v0;
                }
            }
        }

        return parent;
    }

    public int[] Dijkstra(int[][] _m, int _nodes, int _root) {
        double mincost, cost[] = new double[_nodes];
        int frj[], parent[], w0;

        frj = new int[_nodes];
        parent = new int[_nodes];

        for (int w = 0; w < _nodes; w++) {
            parent[w] = -1;
            cost[w] = _m[_root][w];
            frj[w] = _root;
        }

        parent[_root] = _root;
        cost[_root] = 0;

        while (true) {
            mincost = w0 = -1;

            for (int w = 0; w < _nodes; w++) {
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
            for (int w = 0; w < _nodes; w++) {
                if (cost[w] > cost[w0] + _m[w0][w]) {
                    cost[w] = cost[w0] + _m[w0][w];
                    frj[w] = w0;
                }
            }
        }

        return parent;
    }

    public int[] DFS(double[][] _m, int _nodes) {
        int nodeList[], lstLim;
        lstLim = 0;
        nodeList = new int[2 * _nodes - 1];
                
        innerDFS(_m, nodeList, lstLim, 0);
        return nodeList;
    }

    private void innerDFS(double[][] _m, int[] _nodeList, int _lstLim, int _currentNode) {
    }

    public double TwiceAround(double[][] _m, int _nodes) {
        return -1;
    }

    public double TwiceAroundWDijkstra(double[][] _m, int _nodes) {
        return -1;
    }

    public double EdgeScore(double[][] _m, int _nodes) {
        return -1;
    }
}
