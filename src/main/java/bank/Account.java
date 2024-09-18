package bank;

import java.sql.SQLException;

public class Account {

  private int id;
  private String type;
  private double balance;

  public Account(int id, String type, double balance) {
    this.id = id;
    this.type = type;
    this.balance = balance;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(double depositAmt) throws Exception {
    if(depositAmt<1)
    {
      throw new Exception("Enter valid amount");
    }    
    balance = getBalance()+depositAmt;
          try
          {
          DataSource.updateBalance(balance, id);
          System.out.println("The available balance is" + balance);
          }
          catch(SQLException e)
          {
            System.out.println("Balance update failed. Amount not deposited.");
            setBalance(balance-depositAmt);
            System.out.println("Available balance" +balance);
          }
  }
}
