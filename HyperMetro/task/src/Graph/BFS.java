package Graph;

import java.util.*;

public class BFS {
    Graph G;
    boolean[] marked;
    int[] parents;
    ArrayDeque<Integer> queue = new ArrayDeque<>();

    public BFS(Graph G) {
        this.G = G;
        marked = new boolean[G.V()];
        parents = new int[G.V()];
        Arrays.fill(parents, -1);
    }

    public List<Integer> search(int start, int end) {
        System.err.println("search " + start + " " + end);
        ArrayDeque<Integer> result = new ArrayDeque<>();
        marked[start] = true;
        queue.add(start);
        if (scan(end) != -1) {
            int v = end;
            while (parents[v] != -1) {
                result.addFirst(v);
                v = parents[v];
            }
            result.addFirst(v);
        }
        return new ArrayList<>(result);
    }

    private int scan(int end) {
        while (!queue.isEmpty()) {
            int v = queue.pop();
            if (v == end) {
                return v;
            }
            for (int e : G.adj(v)) {
                if (!marked[e]) {
                    marked[e] = true;
                    parents[e] = v;
                    queue.add(e);
                }
            }
        }
        return -1;
    }
}
