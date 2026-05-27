import java.util.*;

class Edge implements Comparable<Edge> {

    char source;
    char destination;
    int weight;

    Edge(char s, char d, int w) {
        source = s;
        destination = d;
        weight = w;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

// Disjoint Set Union (Union-Find)
class DisjointSet {

    Map<Character, Character> parent = new HashMap<>();

    // Make Set
    void makeSet(char vertex) {
        parent.put(vertex, vertex);
    }

    // Find Operation
    char find(char vertex) {

        if (parent.get(vertex) == vertex)
            return vertex;

        char root = find(parent.get(vertex));

        parent.put(vertex, root);

        return root;
    }

    // Union Operation
    void union(char a, char b) {

        char rootA = find(a);
        char rootB = find(b);

        if (rootA != rootB) {
            parent.put(rootA, rootB);
        }
    }
}

public class MumbaiGrid {

    static List<Edge> edges = new ArrayList<>();

    // Add Edge
    static void addEdge(char u, char v, int w) {
        edges.add(new Edge(u, v, w));
    }

    // Kruskal Minimum Spanning Tree
    static void kruskalMST(char[] vertices) {

        Collections.sort(edges);

        DisjointSet ds = new DisjointSet();

        for (char v : vertices) {
            ds.makeSet(v);
        }

        int totalCost = 0;

        System.out.println("Minimum Spanning Tree:\n");

        for (Edge edge : edges) {

            char root1 = ds.find(edge.source);
            char root2 = ds.find(edge.destination);

            if (root1 != root2) {

                System.out.println(
                    edge.source + " - "
                    + edge.destination
                    + " : " + edge.weight);

                totalCost += edge.weight;

                ds.union(root1, root2);
            }
        }

        System.out.println(
            "\nTotal MST Cost = " + totalCost);
    }

    // Bridge Detection using DFS
    static int timer = 0;

    static void bridgeDFS(
            int u,
            boolean[] visited,
            int[] disc,
            int[] low,
            int parent,
            ArrayList<Integer>[] graph,
            char[] names) {

        visited[u] = true;

        disc[u] = low[u] = ++timer;

        for (int v : graph[u]) {

            if (v == parent)
                continue;

            if (!visited[v]) {

                bridgeDFS(v, visited,
                          disc, low,
                          u, graph, names);

                low[u] = Math.min(low[u],
                                  low[v]);

                // Bridge Condition
                if (low[v] > disc[u]) {

                    System.out.println(
                        names[u] + " - "
                        + names[v]
                        + " is a BRIDGE");
                }
            }

            else {

                low[u] = Math.min(low[u],
                                  disc[v]);
            }
        }
    }

    public static void main(String[] args) {

        char[] vertices = {
            'M', 'A', 'B', 'C',
            'D', 'E', 'F', 'G'
        };

        // Add Weighted Edges
        addEdge('A', 'B', 2);
        addEdge('B', 'C', 2);
        addEdge('E', 'F', 2);
        addEdge('C', 'G', 3);
        addEdge('B', 'M', 3);
        addEdge('D', 'E', 3);
        addEdge('M', 'E', 4);
        addEdge('F', 'G', 4);
        addEdge('A', 'M', 4);
        addEdge('M', 'C', 5);
        addEdge('M', 'G', 6);
        addEdge('A', 'D', 7);

        // Run MST
        kruskalMST(vertices);

        // Create Graph for Bridge Detection
        int n = 8;

        ArrayList<Integer>[] graph =
            new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Vertex Mapping
        // 0=M,1=A,2=B,3=C,4=D,5=E,6=F,7=G

        // Connections
        graph[0].add(1);
        graph[1].add(0);

        graph[1].add(2);
        graph[2].add(1);

        graph[2].add(3);
        graph[3].add(2);

        graph[3].add(7);
        graph[7].add(3);

        graph[7].add(6);
        graph[6].add(7);

        graph[6].add(5);
        graph[5].add(6);

        graph[5].add(4);
        graph[4].add(5);

        boolean[] visited = new boolean[n];

        int[] disc = new int[n];
        int[] low = new int[n];

        char[] names = {
            'M', 'A', 'B', 'C',
            'D', 'E', 'F', 'G'
        };

        System.out.println(
            "\nBridge Analysis:\n");

        bridgeDFS(0, visited,
                  disc, low,
                  -1, graph, names);
    }
}