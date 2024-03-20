package server;

import Graph.Edge;
import Graph.GraphData;
import Graph.ShortestPathFinder;
import Graph.VertexCoordinateCalculator;
import Scheduler.GanttChartItem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    private volatile boolean isConnected = true;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            objectInput = new ObjectInputStream(clientSocket.getInputStream());
            objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (isConnected) {
//                String signal = (String) objectInput.readObject();
                byte[] encryptedSignal = (byte[]) objectInput.readObject();
                String signal = (String) decryptObject(encryptedSignal);
                if ("graph".equals(signal)) {
//                    GraphData receivedGraph = (GraphData) objectInput.readObject();
//                    VertexCoordinateCalculator.calculateForceDirectedCoordinates(receivedGraph.getVertices(), receivedGraph.getEdges(), 150.00, 100);
//                    List<Edge> shortedPath = ShortestPathFinder.findShortestPath(receivedGraph);
//                    int cost = ShortestPathFinder.calculateShortestPathCost(receivedGraph);
//                    if (receivedGraph.getVertices().size() >= 10 && receivedGraph.getSource() != null && receivedGraph.getDestination() != null) {
//                        objectOutput.writeObject("graphValid");
//                        objectOutput.flush();
//                        objectOutput.writeObject(receivedGraph);
//                        objectOutput.flush();
//                        objectOutput.writeObject(shortedPath);
//                        objectOutput.flush();
//                        objectOutput.writeObject(cost);
//                        objectOutput.flush();
//                    } else {
//                        objectOutput.writeObject("graphInvalid");
//                        objectOutput.flush();
//                    }
                    byte[] encryptedGraphData = (byte[]) objectInput.readObject();
                    GraphData receivedGraph = (GraphData) decryptObject(encryptedGraphData);
                    VertexCoordinateCalculator.calculateForceDirectedCoordinates(receivedGraph.getVertices(), receivedGraph.getEdges(), 150.00, 100);
                    List<Edge> shortedPath = ShortestPathFinder.findShortestPath(receivedGraph);
                    int cost = ShortestPathFinder.calculateShortestPathCost(receivedGraph);
                    if (receivedGraph.getVertices().size() >= 10 && receivedGraph.getSource() != null && receivedGraph.getDestination() != null) {
//                        objectOutput.writeObject("graphValid");
//                        objectOutput.flush();

                        objectOutput.writeObject(encryptObject("graphValid"));
                        objectOutput.flush();
                        byte[] encryptedResult = encryptObject(receivedGraph);
                        objectOutput.writeObject(encryptedResult);
                        objectOutput.flush();
                        byte[] encryptedshortedPath = encryptObject(shortedPath);
                        objectOutput.writeObject(encryptedshortedPath);
                        objectOutput.flush();
                        byte[] encryptedcost = encryptObject(cost);
                        objectOutput.writeObject(encryptedcost);
                        objectOutput.flush();

                    } else {
//                        objectOutput.writeObject("graphInvalid");
//                        objectOutput.flush();
                        objectOutput.writeObject(encryptObject("graphInvalid"));
                        objectOutput.flush();
                    }

                } else if ("FCFS".equals(signal) || "Priority".equals(signal) || "SJF".equals(signal)) {
//                    List<Scheduler.Process> receivedProcess = (List<Scheduler.Process>) objectInput.readObject();
                    byte[] encryptedProcessData = (byte[]) objectInput.readObject();
                    List<Scheduler.Process> receivedProcess = (List<Scheduler.Process>) decryptObject(encryptedProcessData);
                    List<GanttChartItem> gantt = new ArrayList<>();
                    if (signal.equals("FCFS")) {
                        gantt = Scheduler.Scheduler.scheduleFCFS(receivedProcess);
                    } else if (signal.equals("SJF")) {
                        gantt = Scheduler.Scheduler.scheduleSJF(receivedProcess);
                    } else if (signal.equals("Priority")) {
                        gantt = Scheduler.Scheduler.schedulePriority(receivedProcess);
                    }
                    double averageWaitingTime = Scheduler.Scheduler.calculateAverageWaitingTime(receivedProcess);
                    double averageTurnaroundTime = Scheduler.Scheduler.calculateAverageTurnaroundTime(receivedProcess);
                    if (gantt.size() > 0) {
                        objectOutput.writeObject(encryptObject("schedulerValid"));
                        objectOutput.flush();
//                        objectOutput.writeObject("schedulerValid");
//                        objectOutput.flush();

//                        objectOutput.writeObject(gantt);
//                        objectOutput.flush();
//                        objectOutput.writeObject(receivedProcess);
//                        objectOutput.flush();
//                        objectOutput.writeObject(averageWaitingTime);
//                        objectOutput.flush();
//                        objectOutput.writeObject(averageTurnaroundTime);
//                        objectOutput.flush();
                        byte[] encryptedResult = encryptObject(gantt);
                        objectOutput.writeObject(encryptedResult);
                        objectOutput.flush();
                        byte[] encryptedReceivedProcess = encryptObject(receivedProcess);
                        objectOutput.writeObject(encryptedReceivedProcess);
                        objectOutput.flush();
                        byte[] encryptedAverageWaitingTime = encryptObject(averageWaitingTime);
                        objectOutput.writeObject(encryptedAverageWaitingTime);
                        objectOutput.flush();
                        byte[] encryptedAverageTurnaroundTime = encryptObject(averageTurnaroundTime);
                        objectOutput.writeObject(encryptedAverageTurnaroundTime);
                        objectOutput.flush();
                    } else {
//                        objectOutput.writeObject("schedulerInvalid");
//                        objectOutput.flush();
                        objectOutput.writeObject(encryptObject("schedulerInvalid"));
                        objectOutput.flush();
                    }

                } else if ("RR".equals(signal)) {

//                    List<Scheduler.Process> receivedProcess = (List<Scheduler.Process>) objectInput.readObject();
//                    int quantum = (Integer) objectInput.readObject();
                    byte[] encryptedProcessData = (byte[]) objectInput.readObject();
                    List<Scheduler.Process> receivedProcess = (List<Scheduler.Process>) decryptObject(encryptedProcessData);

                    byte[] encryptedQuantum = (byte[]) objectInput.readObject();
                    int quantum = (Integer) decryptObject(encryptedQuantum);
                    List<GanttChartItem> gantt = Scheduler.Scheduler.scheduleRR(receivedProcess, quantum);
                    double averageWaitingTime = Scheduler.Scheduler.calculateAverageWaitingTime(receivedProcess);
                    double averageTurnaroundTime = Scheduler.Scheduler.calculateAverageTurnaroundTime(receivedProcess);
                    if (gantt.size() > 0) {
                        objectOutput.writeObject(encryptObject("schedulerValid"));
                        objectOutput.flush();
//                        objectOutput.writeObject("schedulerValid");
//                        objectOutput.flush();
//                        objectOutput.writeObject(gantt);
//                        objectOutput.flush();
//
//                        objectOutput.writeObject(receivedProcess);
//                        objectOutput.flush();
//                        objectOutput.writeObject(averageWaitingTime);
//                        objectOutput.flush();
//                        objectOutput.writeObject(averageTurnaroundTime);
//                        objectOutput.flush();
                        byte[] encryptedResult = encryptObject(gantt);
                        objectOutput.writeObject(encryptedResult);
                        objectOutput.flush();
                        byte[] encryptedReceivedProcess = encryptObject(receivedProcess);
                        objectOutput.writeObject(encryptedReceivedProcess);
                        objectOutput.flush();
                        byte[] encryptedAverageWaitingTime = encryptObject(averageWaitingTime);
                        objectOutput.writeObject(encryptedAverageWaitingTime);
                        objectOutput.flush();
                        byte[] encryptedAverageTurnaroundTime = encryptObject(averageTurnaroundTime);
                        objectOutput.writeObject(encryptedAverageTurnaroundTime);
                        objectOutput.flush();
                    } else {
//                        objectOutput.writeObject("schedulerInvalid");
//                        objectOutput.flush();
                        objectOutput.writeObject(encryptObject("schedulerInvalid"));
                        objectOutput.flush();
                    }
                } else if (signal.equals("exit")) {
                    setIsConnected(false);
                    System.out.println("Client ngắt kết nối: " + clientSocket.getInetAddress().getHostAddress());
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Client ngắt kết nối: " + clientSocket.getInetAddress().getHostAddress());
        } catch (Exception ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                objectInput.close();
                objectOutput.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SecretKey generateKeyFromPassword(String password, byte[] salt) throws Exception {
        int iterations = 10000; // số lần lặp
        int keyLength = 128; // độ dài khóa (128, 192, hoặc 256 bits)

        // Tạo một đối tượng KeySpec từ mật khẩu và muối
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

        // Sử dụng SecretKeyFactory để tạo khóa từ KeySpec
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();

        // Sử dụng keyBytes để tạo SecretKey
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        return secretKey;
    }

    private byte[] encryptObject(Object obj) throws Exception {
        String password = "YourSecretPassword"; // Chuỗi mật khẩu
        byte[] salt = new byte[16]; // Một giá trị salt ngẫu nhiên (bạn nên sử dụng giá trị salt thực tế)

        SecretKey generatedKey = generateKeyFromPassword(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, generatedKey);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();

        byte[] objectBytes = byteArrayOutputStream.toByteArray();

        return cipher.doFinal(objectBytes);
    }

    private Object decryptObject(byte[] encryptedBytes) throws Exception {
        String password = "YourSecretPassword"; // Chuỗi mật khẩu
        byte[] salt = new byte[16]; // Một giá trị salt ngẫu nhiên (bạn nên sử dụng giá trị salt thực tế)

        SecretKey generatedKey = generateKeyFromPassword(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, generatedKey);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Object obj = objectInputStream.readObject();
        objectInputStream.close();

        return obj;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
