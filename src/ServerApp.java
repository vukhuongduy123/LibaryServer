import SQLConnection.SQLConnection;
import models.MessageModel;
import socketServer.Server;

public class ServerApp {
    public static void main(String[] args) {
        SQLConnection.initConnection();
        System.out.println(MessageModel.class.getCanonicalName());
        System.out.println("socket server listening");
        Server server = new Server();
        server.listening();
    }
}
