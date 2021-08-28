package Graph;

import java.util.*;

public class BFS {
    Graph G;
//    boolean[] marked;
    int[] parents;
    int[] distances;
    PriorityQueue<Distance> queue = new PriorityQueue<>();

    public BFS(Graph G) {
        this.G = G;
//        marked = new boolean[G.V()];
        parents = new int[G.V()];
        distances = new int[G.V()];
        Arrays.fill(parents, -1);
        Arrays.fill(distances, Integer.MAX_VALUE);
    }

    public int lastDistance(int end) {
        return distances[end];
    }

    public List<Integer> search(int start, int end) {
        System.err.println("search " + start + " " + end);
        ArrayDeque<Integer> result = new ArrayDeque<>();
        queue.add(new Distance(start, 0));
        distances[start] = 0;
        if (scan(end) != -1) {
            int v = end;
            System.err.print("distances: ");
            while (parents[v] != -1) {
                result.addFirst(v);
                System.err.print("(" + v + ") " + distances[v] + " ");
                v = parents[v];
            }
            System.err.println("(" + v + ") " + distances[v]);
            result.addFirst(v);
        }
        return new ArrayList<>(result);
    }

    private int scan(int end) {
        while (!queue.isEmpty()) {
            Distance d = queue.remove();
            if (d.to() == end) {
                return end;
            }
            for (WeightedEdge e : G.adj(d.to())) {
                relax(e);
            }
        }
        return -1;
    }

    private void relax(WeightedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distances[w] > distances[v] + e.weight()) {
            distances[w] = distances[v] + e.weight();
            parents[w] = e.from();
            if (!setDistance(w)) {
                queue.add(new Distance(w, distances[w]));
            }
        }
    }

    private boolean setDistance(int w) {
        Distance dist = queue.stream().filter(it -> it.to() == w).findAny().orElse(null);
        if (dist == null) {
            return false;
        }
        dist.relax(distances[w]);
        return true;
    }

//    public static void main(String[] args) {
//        Graph g = new Graph(5);
//        g.addEdge(0, 1, 1);
//        g.addEdge(1, 0, 1);
//        g.addEdge(1, 2, 1);
//        g.addEdge(2, 1, 1);
//        g.addEdge(2, 3, 1);
//        g.addEdge(3, 2, 1);
//        g.addEdge(3, 4, 1);
//        g.addEdge(4, 3, 1);
//        BFS bfs = new BFS(g);
//        System.out.println(bfs.search(1, 3));
//    }
}