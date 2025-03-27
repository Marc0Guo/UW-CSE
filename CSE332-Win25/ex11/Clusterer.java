import java.util.*;

public class Clusterer {
    private List<List<WeightedEdge<Integer, Double>>> adjList; // the adjacency list of the original graph
    private List<List<WeightedEdge<Integer, Double>>> mstAdjList; // the adjacency list of the minimum spanning tree
    private List<List<Integer>> clusters; // a list of k points, each representing one of the clusters.
    private double cost; // the distance between the closest pair of clusters

    public Clusterer(double[][] distances, int k){
        int nodes = distances.length;
        adjList = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            List<WeightedEdge<Integer, Double>> edges = new ArrayList<>();
            for (int j = 0; j < nodes; j++) {
                if (i != j) {
                    edges.add(new WeightedEdge<>(i, j, distances[i][j]));
                }
            }
            adjList.add(edges);
        }

        prims(0);
        makeKCluster(k);
    }

    // implement Prim's algorithm to find a MST of the graph.
    // in my implementation I used the mstAdjList field to store this.
    private void prims(int start){
        int n = adjList.size();
        double[] key = new double[n];
        Arrays.fill(key, Double.POSITIVE_INFINITY);
        key[start] = 0.0;

        // store parent for each node
        int[] parent = new int[n];
        Arrays.fill(parent, -1); //initialize to be not connected

        PriorityQueue<WeightedEdge<Integer, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
        pq.add(new WeightedEdge<>(-1, start, 0.0));

        boolean[] inMST = new boolean[n];

        while (!pq.isEmpty()) {
            WeightedEdge<Integer, Double> edge = pq.poll();
            int u = edge.destination;

            // skip if already added
            if (inMST[u]) continue;
            inMST[u] = true;

            // if not start node, record parent
            if (edge.source != -1) {
                parent[u] = edge.source;
            }

            // check adjacent edges
            for (WeightedEdge<Integer, Double> adjEdge : adjList.get(u)) {
                int v = adjEdge.destination;
                double weight = adjEdge.weight;

                // if v not added and weightbetter, set u as parent and add to queue
                if (!inMST[v] && weight < key[v]) {
                    key[v] = weight;
                    parent[v] = u;
                    pq.add(new WeightedEdge<>(u, v, weight));
                }
            }
        }

        mstAdjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mstAdjList.add(new ArrayList<>());
        }

        // add edges for each node
        for (int v = 0; v < n; v++) {
            if (parent[v] != -1) {
                int u = parent[v];
                double weight = key[v];
                mstAdjList.get(u).add(new WeightedEdge<>(u, v, weight));
                mstAdjList.get(v).add(new WeightedEdge<>(v, u, weight));
            }
        }
    }


    // After making the minimum spanning tree, use this method to
    // remove its k-1 heaviest edges, then assign integers
    // to clusters based on which nodes are still connected by
    // the remaining MST edges.
    private void makeKCluster(int k){
        // check edge case
        if (k <= 0) {
            throw new IllegalArgumentException("k must be at least 1");
        }


        List<WeightedEdge<Integer, Double>> mstEdges = new ArrayList<>();
        for (int u = 0; u < mstAdjList.size(); u++) {
            for (WeightedEdge<Integer, Double> edge : mstAdjList.get(u)) {
                int v = edge.destination;
                if (v > u) {
                    mstEdges.add(edge);
                }
            }
        }

        // sort edges by weight (descending)
        mstEdges.sort((e1, e2) -> Double.compare(e2.weight, e1.weight));

        int edgesToRemoveCount = k - 1;
        List<WeightedEdge<Integer, Double>> edgesToRemove = new ArrayList<>();
        if (edgesToRemoveCount > 0 && edgesToRemoveCount <= mstEdges.size()) {
            edgesToRemove = mstEdges.subList(0, edgesToRemoveCount);
        }

        if (k == 1) {
            cost = 0.0;
        } else if (!edgesToRemove.isEmpty()) {
            cost = edgesToRemove.get(edgesToRemove.size() - 1).weight;
        } else {
            cost = 0.0;
        }

        // remove selected edges
        for (WeightedEdge<Integer, Double> edge : edgesToRemove) {
            int u = edge.source;
            int v = edge.destination;

            mstAdjList.get(u).removeIf(e -> e.destination == v);
            mstAdjList.get(v).removeIf(e -> e.destination == u);
        }

        // BFS identify connected clusters
        clusters = new ArrayList<>();
        boolean[] visited = new boolean[mstAdjList.size()];
        for (int i = 0; i < mstAdjList.size(); i++) {
            if (!visited[i]) {
                List<Integer> cluster = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);
                visited[i] = true;
                
                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    cluster.add(current);
                    for (WeightedEdge<Integer, Double> e : mstAdjList.get(current)) {
                        int neighbor = e.destination;
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.add(neighbor);
                        }
                    }
                }
                clusters.add(cluster);
            }
        }
    }

    public List<List<Integer>> getClusters(){
        return clusters;
    }

    public double getCost(){
        return cost;
    }

}
