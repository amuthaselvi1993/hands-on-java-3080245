package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We are connected to DB");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return connection;
  }

  public static Customer getCustomer(String username) {
    Customer customer = null;
    String sql = "Select * from customers where username = ?";
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql);) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery();) {
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customer;
  }

  public static Account getAccount(int accountId) {
    Account account = null;
    String sql = "Select * from accounts where id = ?";
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql);) {
      statement.setInt(1, accountId);
      try (ResultSet resultSet = statement.executeQuery();) {
        account = new Account(
            resultSet.getInt("id"),
            resultSet.getString("type"),
            resultSet.getDouble("balance"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

  public static void updateBalance(double balance, int id) throws SQLException {

    String sql = "Update accounts set balance = ? where id = ?";
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql);) {
      statement.setDouble(1, balance);
      statement.setInt(2, id);
      try {
       statement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Balance update failed");
        throw e;
      }
    }
  }

}
