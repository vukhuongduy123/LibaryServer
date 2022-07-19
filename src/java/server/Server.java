package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private final int DEFAULT_PORT = 8080;
    private List<Socket> acceptedSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            acceptedSocket = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listening() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("socket connected");
                acceptedSocket.add(socket);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Thread thread = new Thread(new ClientHandler(objectInputStream, objectOutputStream, socket));
                thread.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
