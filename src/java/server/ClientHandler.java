package server;

import controller.Controller;
import models.MessageModel;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final Socket socket;

    public ClientHandler(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socket) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            MessageModel message;
            try {
                message = (MessageModel) objectInputStream.readObject();
                MessageModel res = Controller.manageMessage(message.getMessage(), message.getArgs());
                objectOutputStream.writeObject(res);
            } catch (Exception e) {
                try {
                    objectInputStream.close();
                    objectInputStream.close();
                    e.printStackTrace();
                    break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
