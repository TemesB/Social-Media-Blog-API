package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    // add dependency

    private MessageDAO messageDAO;
   

    // No argument constructor
    public MessageService() {
        this.messageDAO = new MessageDAO();
      
    }


    public Message addMessage(Message message) {
        // check if the message is valid and added by existing user
        if ((message.getMessage_text().isEmpty()) || (message.getMessage_text().length()>255)){
            return null;
        }
        return  messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }  

    public Message grabMessagesById(int message_id) {
        return messageDAO.getMessageByID(message_id);
       
    }

    public Message clearMessage(int message_id){
        // Retrieve the message
    Message message = messageDAO.getMessageByID(message_id); 
    // If the message doesn't exist, return null
    if (message == null) {
        return null;
    }
    // Delete the message and return it
    messageDAO.deleteMessage(message_id);
    return message;
    }

    public Message amendMessage(int message_id, Message message) {
// check if the text is valid for updating

        if (messageDAO.getMessageByID(message_id) != null && isTextValid(message.getMessage_text())) {
            messageDAO.updateMessage(message_id, message);
            return messageDAO.getMessageByID(message_id);
        } else {
            return null;
        }
    }
    // check if the message is not blank and morethan 255 charactors when updating
    public boolean isTextValid(String message_text){
        if(message_text == null || message_text.isEmpty() || message_text.length() > 255) {
            return false;
        }
        return true;
    }
    
    public List<Message> getMessageByAccountId(int posted_by){

        return messageDAO.getMessageUserID(posted_by);
    } 
}
