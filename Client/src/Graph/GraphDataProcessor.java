package Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//Class để xử lý đồ thị từ file
public class GraphDataProcessor {

    public static Vertex findVertexByName(String vertexName, Set<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            if (vertex.getVertexName().equals(vertexName)) {
                return vertex;
            }
        }
        return null;
    }

    public static GraphData readGraphDataFromFile(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<Edge> edges = new ArrayList<>();
            Set<Vertex> vertices = new HashSet<>();

            String source = null, destination = null;

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Source:") && line.split(": ").length == 2) {
                    source = line.split(": ")[1];
                } else if (line.startsWith("Destination:") && line.split(": ").length == 2) {
                    destination = line.split(": ")[1];
                } else {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String vertextName1 = parts[0];
                        String vertextName2 = parts[1];
                        Vertex vertex1 = findVertexByName(vertextName1, vertices);
                        Vertex vertex2 = findVertexByName(vertextName2, vertices);
                        if (vertex1 == null) {
                            vertex1 = new Vertex(parts[0]);
                        }
                        if (vertex2 == null) {
                            vertex2 = new Vertex(parts[1]);
                        }
                        int weight = Integer.parseInt(parts[2]);
                        edges.add(new Edge(vertex1, vertex2, weight));
                        vertices.add(vertex1);
                        vertices.add(vertex2);
                    }
                }
            }

            bufferedReader.close();
            fileReader.close();

            return new GraphData(vertices, edges, source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
