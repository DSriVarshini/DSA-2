import java.util.*;

class Edge {
    String dest;
    int weight;

    Edge(String dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }
}

class Pair implements Comparable<Pair> {
    String vertex;
    int distance;

    Pair(String vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public int compareTo(Pair other) {
        return this.distance - other.distance;
    }
}

public class GoogleMapsReroute {

    static HashMap<String, ArrayList<Edge>> graph = new HashMap<>();

    static void addEdge(String u, String v, int w) {
        graph.putIfAbsent(u, new ArrayList<Edge>());
        graph.putIfAbsent(v, new ArrayList<Edge>());

        graph.get(u).add(new Edge(v, w));
        graph.get(v).add(new Edge(u, w));
    }

    static void removeEdge(String u, String v) {
        Iterator<Edge> it = graph.get(u).iterator();
        while (it.hasNext()) {
            Edge e = it.next();
            if (e.dest.equals(v)) {
                it.remove();
            }
        }

        it = graph.get(v).iterator();
        while (it.hasNext()) {
            Edge e = it.next();
            if (e.dest.equals(u)) {
                it.remove();
            }
        }
    }

    static void dijkstra(String source, String destination) {

        HashMap<String, Integer> dist = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();

        for (String node : graph.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        dist.put(source, 0);
        pq.add(new Pair(source, 0));

        while (!pq.isEmpty()) {

            Pair current = pq.poll();

            for (Edge edge : graph.get(current.vertex)) {

                int newDist = dist.get(current.vertex) + edge.weight;

                if (newDist < dist.get(edge.dest)) {
                    dist.put(edge.dest, newDist);
                    parent.put(edge.dest, current.vertex);
                    pq.add(new Pair(edge.dest, newDist));
                }
            }
        }

        // Reconstruct path
        ArrayList<String> path = new ArrayList<>();
        String curr = destination;

        while (curr != null) {
            path.add(curr);
            curr = parent.get(curr);
        }

        Collections.reverse(path);

        System.out.println("Shortest Path: " + path);
        System.out.println("Minimum Travel Time: " + dist.get(destination) + " minutes");
    }

    public static void main(String[] args) {

        addEdge("IND", "KOR", 8);
        addEdge("IND", "JPN", 14);
        addEdge("KOR", "MGR", 6);
        addEdge("KOR", "HSR", 5);
        addEdge("MGR", "BTM", 9);
        addEdge("HSR", "BTM", 4);
        addEdge("HSR", "SRJ", 6);
        addEdge("JPN", "SRJ", 8);
        addEdge("BTM", "EC", 12);
        addEdge("SRJ", "EC", 15);
        addEdge("MGR", "HSR", 7);

        System.out.println("Before Road Closure:");
        dijkstra("KOR", "EC");

        removeEdge("MGR", "HSR");

        System.out.println("\nRoad MGR-HSR closed at 09:42");

        System.out.println("\nAfter Re-computation:");
        dijkstra("KOR", "EC");
    }
}