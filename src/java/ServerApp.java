import connection.SQLConnection;
import sercurity.CallbackHandlerImpl;
import server.Server;

import javax.security.auth.login.LoginContext;

public class ServerApp {
    public static void main(String[] args) {
        SQLConnection.initConnection();
        System.setProperty("java.security.auth.login.config", "src/resources/jaas.config");

        System.out.println("socket server listening");

        Server server = new Server();
        server.listening();
    }
}
