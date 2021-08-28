package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private final List<Collection<WeightedEdge>> adj;

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Collection<WeightedEdge> adj(int v) {
        return adj.get(v);
    }

    public int degree(int v) {
        return adj.get(v).size();
    }

    public void addEdge(int v, int w, int weight) {
        E++;
        adj.get(v).add(new WeightedEdge(v, w, weight));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" vertices, ")
                .append(E).append(" edges ").append(NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (WeightedEdge w : adj.get(v)) {
                s.append(w).append(" ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}