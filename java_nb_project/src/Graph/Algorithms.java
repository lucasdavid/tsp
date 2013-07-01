package Graph;

/**
 *
 * @author lucasdavid
 *
 * Describes algorithms that can be used over TSP instances.
 *
 */
public class Algorithms {

    /**
     * @param _m: adjacent matrix of the current graph
     *
     * @return parent[]: a list of nodes parents which represents the minimal
     * spanning tree
     */
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

    /**
     * @param _m adjacent matrix of current graph _root initial node in graph _m
     *
     * @return parent[] list containing parents of nodes in graph _m which
     * represents the shortest path tree
     */
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

    /**
     * @param _parent list containing parents of each node in graph _m _root
     * initial node in _parent tree
     *
     * @return nodeList[] list of visiting sequence over _parent tree, starting
     * from node _root
     */
    public int[] DFS(int[] _parent, int _root) {

        // visiting sequence
        int vs[] = new int[2 * _parent.length - 1];

        innerDFS(_parent, _root, vs, 0);
        return vs;
    }

    /**
     * @param _parent _root root node in current recursive interaction _vs list
     * which represents the visiting sequence in _parent tree _i first unused
     * position of _vs list
     *
     * @return _i current valid position in _nodeLst[]
     */
    private int innerDFS(int[] _parent, int _root, int[] _vs, int _i) {
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
     * @param _m adjacent matrix of current graph
     *
     * @return cost of a minimal circuit candidate
     */
    public double NearestNeighbor(double[][] _m) {
        double cost = 0;
        int current;

        // unreached nodes so far
        int unreached[] = new int[_m.length];
        int nodesLength = _m.length - 1;

        for (int i = 0; i < unreached.length - 1; i++) {
            unreached[i] = i + 1;
        }
        current = unreached[unreached.length - 1] = 0;

        while (nodesLength > 0) {

            int j = 0;
            int nearest = unreached[0];

            for (int i = 1; i < nodesLength; i++) {
                if (_m[current][nearest] > _m[current][unreached[i]]) {
                    nearest = unreached[i];
                    j = i;
                }
            }

            // calculates the traveling cost between [current] and [nearest]
            cost += _m[current][nearest];
            // remove [nearest] node from unreached list
            unreached[j] = unreached[--nodesLength];
            current = nearest;
        }

        // the last cost: traveling between 
        // the final city to the original one
        cost += _m[current][0];
        return cost;
    }

    /**
     * @param _m adjacent matrix of current graph
     *
     * @return cost of a minimal circuit candidate
     */
    public double TwiceAround(double[][] _m) {

        int _mst[] = Prim(_m);
        int visitedNodes[] = DFS(_mst, 0);
        int lstLim = 2 * _m.length - 1;
        
        // for each node in visitedNodes[] list
        for (int i = 0; i < lstLim; i++) {
            
            // for each node after [i]
            for (int j = i +1; j < lstLim; j++) {
                
                // if equals, remove it with shift left over the entire array
                if (visitedNodes[i] == visitedNodes[j]) {
                    for (int k = j +1; k < lstLim; k++) {
                        visitedNodes[k -1] = visitedNodes[k];
                    }
                    
                    // the last position in visitedNodes[]
                    // isn't valid anymore
                    lstLim--;
                }
            } 
        }
        
        for (int i = 0; i < lstLim; i++) {
            System.out.print( visitedNodes[i] + " ");
        }

        double cost = 0;
        for (int i = 1; i < lstLim; i++) {
            cost += _m[ visitedNodes[i - 1]][ visitedNodes[i]];
        }
        
        return cost += _m[visitedNodes[lstLim -1]][0];
    }

    /**
     * @param _m adjacent matrix of current graph
     *
     * @return cost of a minimal circuit candidate
     */
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

    /**
     * Define the score of all edges recursively, where it is the sum between
     * all sub-edges' score plus one.
     *
     * @param _parent list of parents of node _root
     * @param _root initial node
     * @param _scoreM counting matrix
     *
     */
    private int ScoringDFS(int[] _parent, int _root, int[][] _scoreM) {
        int score = 0;

        // score += every sublink's score
        for (int i = 0; i < _parent.length; i++) {
            if (i != _root && _parent[i] == _root) {
                score += ScoringDFS(_parent, i, _scoreM);
            }
        }

        if (_root == _parent[_root]) {
            return 0;
        }

        // it's a leaf, return score 1
        _scoreM[_root][_parent[_root]] += score + 1;
        _scoreM[_parent[_root]][_root] += score + 1;
        return score + 1;
    }

    /**
     * @param _m adjacent matrix of current graph
     *
     * @return cost of a minimal circuit candidate
     */
    public double EdgeScore(double[][] _m)  {

        double cost = 0;
        int edgeScore[][] = new int[_m.length][_m.length];

        // initialize the edgeScore matrix
        for (int i = 0; i < _m.length; i++) {
            for (int j = 0; j < _m.length; j++) {
                edgeScore[i][j] = 0;
            }
        }

        for (int k = 0; k < _m.length; k++) {
            // calculates the shortest-path tree
            int spt[] = Dijkstra(_m, k);

            // calculates the score of each link in spt[]
            ScoringDFS(spt, k, edgeScore);
        }

        int unreachedNodes[] = new int[_m.length];
        int unreachedNodesLength = _m.length - 1;

        for (int i = 0; i < _m.length; i++) {
            unreachedNodes[i] = i + 1;
        }
        int current = unreachedNodes[unreachedNodes.length - 1] = 0;

        // while there are nodes which weren't chose
        while (unreachedNodesLength > 0) {

            int j = 0;
            int reached = unreachedNodes[0];

            for (int i = 1; i < unreachedNodesLength; i++) {

                // choose the link with highest score or, in case 
                // that the two link has the same score, choose 
                // the lighter one (in the original graph).
                if (edgeScore[current][unreachedNodes[i]] > edgeScore[current][reached]
                        || edgeScore[current][unreachedNodes[i]] == edgeScore[current][reached]
                        && _m[current][unreachedNodes[i]] < _m[current][reached]) {

                    reached = unreachedNodes[i];
                    j = i;
                }
            }

            // calculates the cost of traveling between [current] and [reached]
            cost += _m[current][reached];

            // eliminates the node [reached] from the unreached list
            unreachedNodes[j] = unreachedNodes[--unreachedNodesLength];
            current = reached;
        }

        // the cost of returning to the original city
        cost += _m[current][0];
        return cost;
    }
}
