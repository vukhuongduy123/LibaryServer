package controller;

import SQLConnection.SQLConnection;
import models.BooksInfoModel;
import models.MessageModel;
import models.Ultis;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.List;

public class Controller {

    public static MessageModel manageMessage(String msg, Object[] args) {
        switch (msg) {
            case Message.FIND_BOOKS:
                return findBookMessage(args);
            case Message.DELETE_BOOK:
                return deleteBookMessage(args);
            case Message.OPEN:
                return openBookMessage(args);
            case Message.UPDATE_BOOK:
                return updateBookMessage(args);
            case Message.EDIT_BOOK:
                return editBookMessage(args);
        }
        return null;
    }

    private static MessageModel findBookMessage(Object[] args) {
        try {
            Connection connection = SQLConnection.getConnection();
            final String arg = (String) args[0];
            final String sqlQuery = "select * from BooksInfo where BooksInfo.name like " + "'%" + arg + "%' order by BooksInfo.name";
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

    private static MessageModel deleteBookMessage(Object[] args) {
        try {
            Connection connection = SQLConnection.getConnection();
            final BooksInfoModel booksInfoModel = (BooksInfoModel) args[0];
            final String sqlQuery = "delete from BooksInfo where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, booksInfoModel.getId());
            boolean res = preparedStatement.execute();
            MessageModel messageModel = new MessageModel();
            messageModel.setMessage(String.valueOf(res));
            return messageModel;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static MessageModel openBookMessage(Object[] args) {
        try {
            Connection connection = SQLConnection.getConnection();
            final BooksInfoModel booksInfoModel = (BooksInfoModel) args[0];
            final String sqlQuery = "select content from BooksContent where refId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, booksInfoModel.getBookContentId());
            ResultSet resultSet = preparedStatement.executeQuery();
            MessageModel messageModel = new MessageModel();
            if (resultSet.next()) {
                messageModel.setMessage("success");
                messageModel.setArgs(new Object[]{resultSet.getBytes(1)});
            } else {
                messageModel.setMessage("fail");
            }
            return messageModel;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static MessageModel updateBookMessage(Object[] args) {
        try {
            Connection connection = SQLConnection.getConnection();
            final BooksInfoModel booksInfoModel = (BooksInfoModel) args[0];
            final byte[] bytes = (byte[]) args[1];
            final String url = "E:\\Self Study Course\\BookSQL\\Books\\" + booksInfoModel.getName();
            FileOutputStream stream = new FileOutputStream(url);
            stream.write(bytes);
            stream.close();
            String sqlQuery = "exec dbo.AddNewBook ?,?,?,?,?,?,?,?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, booksInfoModel.getName());
            preparedStatement.setString(2, booksInfoModel.getAuthour());
            preparedStatement.setString(3, booksInfoModel.getDescr());
            preparedStatement.setString(4, booksInfoModel.getCategories());
            preparedStatement.setString(5, booksInfoModel.getTranslator());
            preparedStatement.setDate(6, new java.sql.Date(booksInfoModel.getPublished().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(booksInfoModel.getAddedTime().getTime()));
            preparedStatement.setString(8, url);
            boolean res = preparedStatement.execute();
            MessageModel messageModel = new MessageModel(String.valueOf(res), new Object[]{booksInfoModel});
            return messageModel;

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static MessageModel editBookMessage(Object[] args) {
        try {
            Connection connection = SQLConnection.getConnection();
            BooksInfoModel booksInfoModel = (BooksInfoModel) args[0];
            String sqlQuery = "update BooksInfo set \n" +
                    "BooksInfo.name = ?,\n" +
                    "                    BooksInfo.authour = ?,\n" +
                    "                    BooksInfo.descr = ?,\n" +
                    "                    BooksInfo.categories = ?,\n" +
                    "                    BooksInfo.translator = ?,\n" +
                    "                    BooksInfo.published = ? \n" +
                    "                    where BooksInfo.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, booksInfoModel.getName());
            preparedStatement.setString(2, booksInfoModel.getAuthour());
            preparedStatement.setString(3, booksInfoModel.getDescr());
            preparedStatement.setString(4, booksInfoModel.getCategories());
            preparedStatement.setString(5, booksInfoModel.getTranslator());
            preparedStatement.setDate(6, new java.sql.Date(booksInfoModel.getPublished().getTime()));
            preparedStatement.setInt(7, booksInfoModel.getId());
            int res = preparedStatement.executeUpdate();

            MessageModel messageModel = new MessageModel(String.valueOf(res), new Object[]{booksInfoModel});
            return messageModel;

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
