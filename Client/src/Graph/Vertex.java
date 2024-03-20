package Graph;

import java.io.Serializable;

public class Vertex implements Serializable {

    private String vertexName;
    private int X;
    private int Y;

    public Vertex(String vertexName) {
        this.vertexName = vertexName;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int X) {
        this.X = X;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public String getVertexName() {
        return vertexName;
    }

    public void setVertexName(String vertexName) {
        this.vertexName = vertexName;
    }

}
