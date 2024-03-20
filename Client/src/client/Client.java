package client;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientInterface().setVisible(true);
            }
        });

    }
}
