package Graph;

import java.io.Serializable;
//Chứa các thuộc tính về cạnh của đồ thị
public class Edge implements Serializable {

    private Vertex vertex1;
    private Vertex vertex2;
    private int weight;

    public void updateStartPoint(Vertex newStartVertex) {
        this.vertex1 = newStartVertex;
    }

    public Edge(Vertex vertex1, Vertex vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public void updateEndPoint(Vertex newEndVertex) {
        this.vertex2 = newEndVertex;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public int getWeight() {
        return weight;
    }
}
