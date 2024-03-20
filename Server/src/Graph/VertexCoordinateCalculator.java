package Graph;

import java.util.List;
import java.util.Set;
import java.util.Random;
//Class để sắp xếp lại các đỉnh cho cân đổi 
public class VertexCoordinateCalculator {

    public static void calculateForceDirectedCoordinates(Set<Vertex> vertices, List<Edge> edges, double k, int maxIterations) {
        double repulsiveForce = 500.00; // Lực đẩy
        double attractiveForce = 0.1; // Lực hút

        // Khởi tạo tọa độ ngẫu nhiên ban đầu cho các đỉnh
        Random rand = new Random();
        for (Vertex vertex : vertices) {
            vertex.setX(rand.nextInt(1300)); // Giới hạn tọa độ INT theo nhu cầu
            vertex.setY(rand.nextInt(600));
        }

        for (int iteration = 0; iteration < maxIterations; iteration++) {

            for (Vertex vertex : vertices) {
                int totalForceX = 0;
                int totalForceY = 0;

                for (Vertex other : vertices) {
                    if (vertex != other) {
                        // Tính lực đẩy
                        int dx = other.getX() - vertex.getX();
                        int dy = other.getY() - vertex.getY();
                        double distance = Math.max(Math.sqrt(dx * dx + dy * dy), 1.0);
                        double force = repulsiveForce / (distance * distance);
                        totalForceX += (dx / distance) * force;
                        totalForceY += (dy / distance) * force;
                    }
                }

                for (Edge edge : edges) {
                    if (edge.getVertex1() == vertex) {
                        Vertex neighbor = edge.getVertex2();
                        int dx = neighbor.getX() - vertex.getX();
                        int dy = neighbor.getY() - vertex.getY();
                        double distance = Math.max(Math.sqrt(dx * dx + dy * dy), 1.0);
                        double force = attractiveForce * (distance - k);
                        totalForceX += (dx / distance) * force;
                        totalForceY += (dy / distance) * force;
                    } else if (edge.getVertex2() == vertex) {
                        Vertex neighbor = edge.getVertex1();
                        int dx = neighbor.getX() - vertex.getX();
                        int dy = neighbor.getY() - vertex.getY();
                        double distance = Math.max(Math.sqrt(dx * dx + dy * dy), 1.0);
                        double force = attractiveForce * (distance - k);
                        totalForceX += (dx / distance) * force;
                        totalForceY += (dy / distance) * force;
                    }
                }

                // Cập nhật tọa độ của đỉnh dựa trên tổng lực
                int newX = vertex.getX() + totalForceX;
                int newY = vertex.getY() + totalForceY;
                vertex.setX(newX);
                vertex.setY(newY);

            }
        }
    }

}
