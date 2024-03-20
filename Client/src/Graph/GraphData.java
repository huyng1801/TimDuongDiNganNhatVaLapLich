package Graph;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class GraphData implements Serializable {

    private Set<Vertex> vertices;
    private List<Edge> edges;
    private String source;
    private String destination;

    public GraphData(Set<Vertex> vertices, List<Edge> edges, String source, String destination) {
        this.vertices = vertices;
        this.edges = edges;
        this.source = source;
        this.destination = destination;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Set<Vertex> getVertices() {

        return vertices;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

}
