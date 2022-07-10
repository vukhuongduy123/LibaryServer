import SQLConnection.SQLConnection;
import models.BooksInfoModel;
import models.MessageModel;
import socketServer.Server;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ServerApp {
    public static void main(String[] args) {
        SQLConnection.initConnection();

        /*try {
            Connection connection = SQLConnection.getConnection();
            BooksInfoModel booksInfoModel = new BooksInfoModel();
            booksInfoModel.setName("StartingoutCplusplus.pdf");
            booksInfoModel.setAuthour("Tony");
            booksInfoModel.setDescr("C++ book");
            booksInfoModel.setCategories("Programming");
            booksInfoModel.setTranslator(null);
            java.util.Date date = new SimpleDateFormat("YYYY-MM-DD").parse("2019-01-01");

            booksInfoModel.setPublished(date);
            booksInfoModel.setAddedTime(new java.util.Date());
            final String url = "E:\\Self Study Course\\BookSQL\\Books\\Software_Engineering_9th_Edition.pdf";
            String sqlQuery = "exec dbo.AddNewBook ?,?,?,?,?,?,?,?";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, booksInfoModel.getName());
            preparedStatement.setString(2, booksInfoModel.getAuthour());
            preparedStatement.setString(3, booksInfoModel.getDescr());
            preparedStatement.setString(4, booksInfoModel.getCategories());
            preparedStatement.setString(5, booksInfoModel.getTranslator());
            preparedStatement.setDate(6, new java.sql.Date(booksInfoModel.getPublished().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(booksInfoModel.getAddedTime().getTime()));
            preparedStatement.setString(8, url);
            boolean res = preparedStatement.execute();
            System.out.println(res);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }*/


        System.out.println("socket server listening");

        Server server = new Server();
        server.listening();
    }
}
