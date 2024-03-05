package DAO;


import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {

            // Create statement
            String sql = " INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            // Execute the Statement
            preparedStatement.executeUpdate();

            // Process the result
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                int generated_message_id = rs.getInt(1);
                 return new Message (generated_message_id, message.getPosted_by(),
                        message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       
        return null;
    }

    // Method to retrieve all messages
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();

        try {
            // Create Statement
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the statement
            ResultSet rs = preparedStatement.executeQuery();

            // Process the result
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

// Method to retrieve all messages by message id
    public Message getMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {      

            // Create Statement
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            // Execute the statement
            ResultSet rs = preparedStatement.executeQuery();

            // Process the result
            while (rs.next()) {
                Message newMessage =  new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return  newMessage;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

// Methode to delete message by message id
    public void deleteMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            // create Statement
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            // execute the statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to update the message 
     public Message updateMessage(int message_id, Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            // Create the statement
            String sql = " UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);

            // Execute the statement
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    // Message to retrieve all messages filtered by posted_by
    public List<Message> getMessageUserID(int posted_by) {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();

        try {      
            // Create statement
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);

            // Execute the statement
            ResultSet rs = preparedStatement.executeQuery();

            // Process the result
            while (rs.next()) {
                Message newMessage = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(newMessage);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
