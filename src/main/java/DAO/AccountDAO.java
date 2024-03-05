
package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;


public class AccountDAO {
    // Method to add a new account to the database
    public Account addAccount(Account account) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            // create statement
            String sql = "INSERT INTO account (username, password) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // execute statement
            preparedStatement.executeUpdate();

            // process results
            ResultSet rs= preparedStatement.getGeneratedKeys();
            while (rs.next()){
                int generated_account_id = rs.getInt(1);
                return new Account(generated_account_id , account.getUsername(), account.getPassword());
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Method to retrieve an account by username and password for login
    public Account getAccount(Account account){

        Connection connection = ConnectionUtil.getConnection();
        try {
            // Create statement

            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // Execute Statement
            ResultSet rs = preparedStatement.executeQuery();

            // Process the result
            while(rs.next()) {
                Account logedAccount = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return  logedAccount;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
    //  Method to retrieve all accounts by its Id
    public Account getAccountByID(int account_id){

        Connection connection = ConnectionUtil.getConnection();

        try {
        //    Create Statement
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            // Execute the statement
            ResultSet rs = preparedStatement.executeQuery();

            // Process the result
            while(rs.next()){
                Account accountById = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return  accountById;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Method to retrieve all accounts by it Username
public Account getAccountByUserName(String username){

        Connection connection = ConnectionUtil.getConnection();

        try {
            //Create Statement
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            // Execute the statement
            ResultSet rs = preparedStatement.executeQuery();

            // Process the result
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return  account;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}