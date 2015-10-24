package Graph;

/**
 *
 * @author lucasdavid
 */
public abstract class Graph {

    protected double[][] m;

    public double at(int _a, int _b) throws Exception {
        return m[_a][_b];
    }

    /**
     * @return a list of nodes parents which represents the minimal spanning
     * tree.
     */
    public int[] Prim() {
        int parent[], frj[];
        double cost[], mincost;

        cost = new double[m.length];
        frj = new int[m.length];
        parent = new int[m.length];

        int w, v0;
        v0 = 0;

        for (w = 1; w < m.length; w++) {
            parent[w] = -1;
            frj[w] = 0;
            cost[w] = m[0][w];
        }
        parent[0] = 0;
        while (true) {
            mincost = -1;
            for (w = 0; w < m.length; w++) {
                if (parent[w] == -1 && (mincost > cost[w] || mincost == -1)) {
                    mincost = cost[v0 = w];
                }
            }

            if (mincost == -1) {
                break;
            }

            parent[v0] = frj[v0];
            for (w = 0; w < m.length; w++) {
                if (parent[w] == -1 && cost[w] > m[v0][w]) {
                    cost[w] = m[v0][w];
                    frj[w] = v0;
                }
            }
        }
        return parent;
    }

    /**
     * @param _root initial node in the graph m.
     *
     * @return list containing parents of nodes in graph _m which represents the
     * shortest path tree.
     */
    int[] Dijkstra(int _root) {
        double mincost, cost[] = new double[m.length];
        int frj[], parent[], w0;

        frj = new int[m.length];
        parent = new int[m.length];

        for (int w = 0; w < m.length; w++) {
            parent[w] = -1;
            cost[w] = m[_root][w];
            frj[w] = _root;
        }

        parent[_root] = _root;
        cost[_root] = 0;
        w0 = 0;

        while (true) {
            mincost = -1;

            for (int w = 0; w < m.length; w++) {
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
            for (int w = 0; w < m.length; w++) {
                if (cost[w] > cost[w0] + m[w0][w]) {
                    cost[w] = cost[w0] + m[w0][w];
                    frj[w] = w0;
                }
            }
        }
        return parent;
    }

    /**
     * @param _parent list containing parents of each node in graph _m
     * @param _root initial node in _parent tree
     *
     * @return list of visiting sequence over _parent tree, starting from node
     * _root
     */
    int[] DFS(int[] _parent, int _root) {
        // visiting sequence
        int vs[] = new int[2 * _parent.length - 1];

        innerDFS(_parent, _root, vs, 0);
        return vs;
    }

    /**
     * @param _parent
     * @param _root node in current recursive interaction
     * @param _vs list that represents the visiting sequence in _parent tree
     * @param _i first unused position of _vs list
     *
     * @return current valid position in _nodeLst[]
     */
    int innerDFS(int[] _parent, int _root, int[] _vs, int _i) {
        _vs[_i++] = _root;

        for (int i = 0; i < _parent.length; i++) {
            if (i != _root && _parent[i] == _root) {
                _i = innerDFS(_parent, i, _vs, _i);
                _vs[_i++] = _root;
            }
        }

        return _i; // return current valid position
    }

    /**
     * @return cost of a minimal circuit candidate.
     */
    public double NearestNeighbor() {
        double cost = 0;
        int current;

        // unreached nodes so far
        int unreached[] = new int[m.length];
        int nodesLength = m.length - 1;

        for (int i = 0; i < unreached.length - 1; i++) {
            unreached[i] = i + 1;
        }
        current = unreached[unreached.length - 1] = 0;

        while (nodesLength > 0) {

            int j = 0;
            int nearest = unreached[0];

            for (int i = 1; i < nodesLength; i++) {
                if (m[current][nearest] > m[current][unreached[i]]) {
                    nearest = unreached[i];
                    j = i;
                }
            }

            // calculates the traveling cost between [current] and [nearest]
            cost += m[current][nearest];
            // remove [nearest] node from unreached list
            unreached[j] = unreached[--nodesLength];
            current = nearest;
        }

        // the last cost: traveling between 
        // the final city to the original one
        cost += m[current][0];
        return cost;
    }

    /**
     * Original Twice-around algorithm.
     *
     * @return cost of a minimal circuit candidate.
     */
    public double TwiceAround() {
        int mst[] = Prim();
        return TwiceAround(mst);
    }

    /**
     * Modified Twice-around algorithm.
     *
     * This instance of Twice-around will use a shortest-path tree as input,
     * instead of the original minimal spanning tree.
     *
     * @return cost of a minimal circuit candidate.
     */
    public double TwiceAroundDijkstra() {
        int spt[] = Dijkstra(0);
        return TwiceAround(spt);
    }

    public double TwiceAround(int[] inputTree) {
        int visitedNodes[] = DFS(inputTree, 0);
        int lstLim = 2 * m.length - 1;

        // for each node in visitedNodes[] list
        for (int i = 0; i < lstLim; i++) {

            // for each node after [i]
            for (int j = i + 1; j < lstLim; j++) {

                // if equals, remove it with shift left over the entire array
                if (visitedNodes[i] == visitedNodes[j]) {
                    for (int k = j + 1; k < lstLim; k++) {
                        visitedNodes[k - 1] = visitedNodes[k];
                    }

                    // the last position in visitedNodes[]
                    // isn't valid anymore
                    lstLim--;
                }
            }
        }

        double cost = 0;
        for (int i = 1; i < lstLim; i++) {
            cost += m[visitedNodes[i - 1]][visitedNodes[i]];
        }

        return cost += m[visitedNodes[lstLim - 1]][0];
    }
}
