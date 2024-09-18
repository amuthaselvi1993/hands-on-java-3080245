package bank;

import java.nio.channels.Pipe.SourceChannel;
import java.sql.SQLException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);
    Customer customer = menu.authenticateCustomer();
    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }
    menu.scanner.close();

  }

  private Customer authenticateCustomer() {

    System.out.println("Please enter your username");
    String username = scanner.next();
    System.out.println("Please enter password");
    String password = scanner.next();
    try {
      Customer customer = Authenticator.login(username, password);
      System.out.println("You are now logged in");
      return customer;
    } catch (LoginException e) {
      System.out.println(e.getMessage());
      return null;
    }

  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;
    while (selection != 9 && customer.isLoggedIn()) {
      System.out.println("1. Available Balance");
      System.out.println("2. Deposit");
      System.out.println("3. Withdraw");
      System.out.println("4. Logout");
      selection = scanner.nextInt();
      switch (selection) {
        case 1: {
          System.out.println("The available balance is" + account.getBalance());
          break;
        }
        case 2: {
          System.out.println("Please enter the amount to be deposited");
          double depositAmt = scanner.nextDouble();
          try {
            account.deposit(depositAmt);
          } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Try again");
          }
          break;
        }
        case 3: {
          break;
        }
        case 4: {
          customer.setLoggedIn(false);
          System.out.println("Thank you for banking");
          break;
        }
      }
    }
  }
}
