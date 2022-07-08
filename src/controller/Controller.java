package controller;

import SQLConnection.SQLConnection;
import models.BooksInfoModel;
import models.MessageModel;
import models.Ultis;

import java.sql.*;
import java.util.List;

public class Controller {

    public static MessageModel manageMessage(String msg, Object[] args) {
        switch (msg) {
            case Message.FIND_BOOK:
                return findBookMessage(args);
        }
        return null;
    }

    private static MessageModel findBookMessage(Object[] args) {
        try {
            Connection connection = SQLConnection.getConnection();
            final String arg = (String) args[0];
            final String sqlQuery = "select * from BooksInfo where name like " + "'%" + arg + "%'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            List<BooksInfoModel> booksInfos = Ultis.mappingModelFromResultSet(resultSet, BooksInfoModel.class);
            MessageModel messageModel = new MessageModel();
            messageModel.setMessage("success");
            messageModel.setArgs(booksInfos.toArray());
            return messageModel;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
