package controller;

import SQLConnection.SQLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.sound.midi.SysexMessage;
import java.sql.*;

public class Controller {
    public String manageMessage(String msg, Object[] args) {
        String res = "";
        switch (msg) {
            case Message.FIND_BOOK:
                res = findBookMessage(args);
                break;
        }
        return res;
    }

    private String findBookMessage(Object[] args) {
        try {
            Connection connection = SQLConnection.getConnection();
            final String arg = (String) args[0];
            final String sqlQuery = "select * from BooksInfo where name like " + "'%" + arg + "%'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            JsonArray json = new JsonArray();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while(resultSet.next()) {
                int numColumns = resultSetMetaData.getColumnCount();
                JsonObject obj = new JsonObject();
                for (int i = 1; i <= numColumns; i++) {
                    final String column_name = resultSetMetaData.getColumnName(i);
                    Object data =  resultSet.getObject(column_name);
                    obj.addProperty(column_name, data == null ? "null" : data.toString());
                }
                json.add(obj);
            }
            return json.toString();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
