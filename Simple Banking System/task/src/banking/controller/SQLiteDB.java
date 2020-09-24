package banking.controller;

import banking.CardController;
import banking.model.Card;
import banking.model.User;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLiteDB {
    private String dbUrl;
    private SQLiteDataSource dataSource;
    private Connection connection;

    public SQLiteDB() {

    };

    public void createDataBase(String fileName) {
        dbUrl = "jdbc:sqlite:" + fileName;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(dbUrl);
        try (Connection connection = dataSource.getConnection();) {
            connection.createStatement().execute(CardController.cardTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addUser(User user) {
        int result = 0;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Card (number, pin, balance) VALUES (?, ?, ?);");
            preparedStatement.setString(1, user.getCard().getCardNumber());
            preparedStatement.setString(2, user.getCard().getPin());
            preparedStatement.setInt(3, user.getBalance());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteUser(User user) {
        int result = 0;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Card WHERE id=?");
            preparedStatement.setInt(1, user.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    public User getUserByCardNumberAndPin(String cardNumber, String pin) {
        User user= getUserByCardNumber(cardNumber);
        System.out.println(user);
        return user != null && user.getCard().getPin().equals(pin) ? user : null;
    }

    private User getUserByCardNumber(String cardNumber) {
        User user= null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Card WHERE number=?");
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Card c = new Card(resultSet.getString("number"), resultSet.getString("pin"));
                user = new User();
                user.setCard(c);
                user.setId(resultSet.getInt("id"));
                user.setBalance(resultSet.getInt("balance"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    public int addIncome(User user, int income) {
        int newBalance = user.getBalance() + income;
        int result = 0;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE card SET balance=? WHERE number=?");
            preparedStatement.setInt(1, newBalance);
            preparedStatement.setString(2, user.getCard().getCardNumber());
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                user.setBalance(newBalance);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public int doTransfer(User from, String cardNumberTo, int amount) {
        User target = getUserByCardNumber(cardNumberTo);
        int result = addIncome(target, amount);
        if (result > 0) {
            addIncome(from, - amount);
        }

        return  result;
    }

    public boolean containsCardNumber(String cardNumber) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT id FROM card WHERE number=?");
            preparedStatement.setString(1, cardNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        SQLiteDB db = new SQLiteDB();
        db.createDataBase("test.db");
    }*/
}
