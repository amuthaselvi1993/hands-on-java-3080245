package bank;

import javax.security.auth.login.LoginException;

public class Authenticator {

  public static Customer login(String username, String password) throws LoginException {
    Customer customer = DataSource.getCustomer(username);
    if (customer == null) {
      throw new LoginException("User not found");
    }
    if (password.equals(customer.getPassword())) {
      customer.setLoggedIn(true);
    }
    else{
      throw new LoginException("Incorrect password");
    }
    return customer;
  }
  public static Customer logout(Customer customer)
  {
    customer.setLoggedIn(false);
    return customer;
  }

}
