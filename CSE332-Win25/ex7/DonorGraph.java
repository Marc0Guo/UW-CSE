import java.util.*;

public class DonorGraph {
    private List<List<Match>> adjList;

    // The donatingTo array indicates which repient each donor is
    // affiliated with. Specifically, the donor at index i has volunteered
    // to donate a kidney on behalf of recipient donatingTo[i].
    // The matchScores 2d array gives the match scores associated with each
    // donor-recipient pair. Specificically, matchScores[x][y] gives the
    // HLA score for donor x and reciplient y.
    public DonorGraph(int[] donorToBenefit, int[][] matchScores){
        // TODO
        adjList = new ArrayList<>();
        // n = # of recepients (nodes in graph)
        int n = (matchScores.length > 0) ? matchScores[0].length : 0;

        // Initialize adjlist for each recepient
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        // iterate over doner
        for (int donor = 0; donor < donorToBenefit.length; donor++) {
            int beneficiary = donorToBenefit[donor];
            int[] scores = matchScores[donor];
            // iterate over recipient
            for (int recipient = 0; recipient < scores.length; recipient++) {
                //score check for each recepient
                if (scores[recipient] >= 60) {
                    adjList.get(beneficiary).add(new Match(donor, beneficiary, recipient));
                }
            }
        }
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public boolean isAdjacent(int start, int end){
        for(Match m : adjList.get(start)){
            if(m.recipient == end)
                return true;
        }
        return false;
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public int getDonor(int beneficiary, int recipient){
        for(Match m : adjList.get(beneficiary)){
            if(m.recipient == recipient)
                return m.donor;
        }
        return -1;
    }


    // returns a chain of matches to make a donor cycle
    // which includes the given recipient.
    // Returns an empty list if no cycle exists.
    public List<Match> findCycle(int recipient){
        // TODO
        int n = adjList.size(); // # of nodes
        if (n == 0 || recipient < 0 || recipient >= n) return new ArrayList<>();

        Map<Integer, Match> parent = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[n];

        // check all out going edges from each recipient
        for (Match edge : adjList.get(recipient)) {
            int next = edge.recipient;
            if (next == recipient) {
                return new ArrayList<>(List.of(edge));
            }
            // if not visided, mark and push to stack
            if (!visited[next]) {
                parent.put(next, edge);
                visited[next] = true;
                stack.push(next);
            }
        }

        // DFS using class example
        while (!stack.isEmpty()) {
            int current = stack.pop();

            for (Match edge : adjList.get(current)) {
                int next = edge.recipient;

                if (next == recipient) {
                    List<Match> cycle = new ArrayList<>();
                    cycle.add(edge);
                    int node = current;
                    while (node != recipient) {
                        Match prevEdge = parent.get(node);
                        if (prevEdge == null) {
                            return new ArrayList<>();
                        }
                        cycle.add(0, prevEdge);
                        node = prevEdge.beneficiary;
                    }
                    return cycle;
                }

                if (!visited[next]) {
                    parent.put(next, edge);
                    visited[next] = true;
                    stack.push(next);
                }
            }
        }
        return new ArrayList<>();
    }

    // returns true or false to indicate whether there
    // is some cycle which includes the given recipient.
    public boolean hasCycle(int recipient){
        // TODO
        int n = adjList.size();
        if (n == 0 || recipient < 0 || recipient >= n) return false;

        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        // check all out going edges from each recipient
        for (Match edge : adjList.get(recipient)) {
            int next = edge.recipient;
            if (next == recipient) {
                return true;
            }
            if (!visited[next]) {
                visited[next] = true;
                stack.push(next);
            }
        }

        // DFS using class example
        while (!stack.isEmpty()) {
            int current = stack.pop();

            for (Match edge : adjList.get(current)) {
                int next = edge.recipient;
                if (next == recipient) {
                    return true;
                }
                if (!visited[next]) {
                    visited[next] = true;
                    stack.push(next);
                }
            }
        }

        return false;
    }
}
