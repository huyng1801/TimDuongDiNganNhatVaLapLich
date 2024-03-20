package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        int port = 12345;
        String ipAddress; 

        try {
            // Nhập địa chỉ IP từ người dùng
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập địa chỉ IP: ");
            ipAddress = sc.nextLine();

            // Tạo server socket và lắng nghe kết nối từ clients
            InetAddress serverAddress = InetAddress.getByName(ipAddress);
            ServerSocket serverSocket = new ServerSocket(port, 0, serverAddress);
            System.out.println("Server đang chạy trên IP: " + ipAddress + ", Port: " + port);

            while (true) {
                // Chấp nhận kết nối từ một client và tạo một thread mới để xử lý kết nối đó
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client kết nối từ: " + clientSocket.getInetAddress().getHostAddress());
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
