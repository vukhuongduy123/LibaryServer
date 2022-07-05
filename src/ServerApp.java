import SQLConnection.SQLConnection;
import controller.Controller;
import controller.Message;

public class ServerApp {
    public static void main(String[] args){
        SQLConnection.initConnection();
        Controller controller = new Controller();
        controller.manageMessage(Message.FIND_BOOK, new Object[]{"C"});
    }
}
