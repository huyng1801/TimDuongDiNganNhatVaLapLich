package Graph;

import java.util.*;
//Class để tìm đường đi ngắn nhất và chi phí
public class ShortestPathFinder {

    public static List<Edge> findShortestPath(GraphData graphData) {
        Set<Vertex> vertices = graphData.getVertices();
        List<Edge> edges = graphData.getEdges();
        String source = graphData.getSource();
        String destination = graphData.getDestination();

        Map<Vertex, Double> distance = new HashMap<>();
        Map<Vertex, Vertex> previous = new HashMap<>();

        for (Vertex vertex : vertices) {
            distance.put(vertex, Double.MAX_VALUE);
        }

        Vertex sourceVertex = null;
        for (Vertex vertex : vertices) {
            if (vertex.getVertexName().equals(source)) {
                sourceVertex = vertex;
                break;
            }
        }

        if (sourceVertex == null) {
            return new ArrayList<>();
        }

        distance.put(sourceVertex, 0.0);

        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
        queue.add(sourceVertex);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();

            if (current.getVertexName().equals(destination)) {
                List<Edge> shortestPath = new ArrayList<>();
                Vertex vertex = current;
                while (previous.containsKey(vertex)) {
                    Vertex prev = previous.get(vertex);
                    for (Edge edge : edges) {
                        if ((edge.getVertex1() == prev && edge.getVertex2() == vertex)
                                || (edge.getVertex2() == prev && edge.getVertex1() == vertex)) {
                            shortestPath.add(edge);
                            break;
                        }
                    }
                    vertex = prev;
                }
                Collections.reverse(shortestPath);
                return shortestPath;
            }

            for (Edge edge : edges) {
                if (edge.getVertex1() == current || edge.getVertex2() == current) {
                    Vertex neighbor = (edge.getVertex1() == current) ? edge.getVertex2() : edge.getVertex1();
                    double alt = distance.get(current) + edge.getWeight();
                    if (alt < distance.get(neighbor)) {
                        distance.put(neighbor, alt);
                        previous.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    public static int calculateShortestPathCost(GraphData graphData) {
        List<Edge> shortestPath = findShortestPath(graphData);
        int cost = 0;
        for (Edge edge : shortestPath) {
            cost += edge.getWeight();
        }
        return cost;
    }
}
