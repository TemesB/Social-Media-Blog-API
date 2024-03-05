package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    // Add dependency
        private  AccountService accountService;
        private  MessageService messageService;
        private  ObjectMapper objectMapper;
       
    
        public SocialMediaController() {
            this.accountService = new AccountService();
            this.messageService = new MessageService();
            this.objectMapper = new ObjectMapper();
         
        }
        public SocialMediaController(AccountService accountService, MessageService messageService,ObjectMapper objectMapper ){
            this.accountService = accountService;
            this.messageService = messageService;
            this.objectMapper = objectMapper;
    
        }
        // Method to start Javalin
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // Create endpoints
        app.post("/register", this::registerAccount);
        app.post("/login", this::login);
        app.post("/messages", this::postMessages);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getMessageByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // Create handlers
    // Exceptions are thrown in the provided methods below to handle potential errors or exceptional situations
    //  that may occur during IO operations, JSON processing, or multithreading. 
    private void registerAccount(Context context) throws IOException {
        // get information needed from request
        Account account = objectMapper.readValue(context.body(), Account.class);

        // calling relevant service layer method
        Account newAccount = accountService.registerNewAcct(account);
        // check and process the result
        if (newAccount != null) {
            context.result(objectMapper.writeValueAsString(newAccount));
        } else {
            context.status(400);
        }
    }

    private void login(Context context)throws IOException {
        // get information needed from request
        Account account = objectMapper.readValue(context.body(), Account.class);
        // calling relevant service layer method
        Account userLogin = accountService.authenticate(account);
        // Process the result
        if (userLogin != null) {
            context.json(objectMapper.writeValueAsString(userLogin));
        } else {
            context.status(401);
        }
    }

    private void postMessages(Context context) throws JsonProcessingException {

        // get information needed from request
        Message message = objectMapper.readValue(context.body(), Message.class);
        // calling relevant service layer method
        Message newMessage = messageService.addMessage(message);
          // Process the result
        if (newMessage != null) {
            context.json(objectMapper.writeValueAsString(newMessage));
        } else {
            context.status(400);
        }
    }
    private void getAllMessages(Context context) {
        // process the result
        context.json(messageService.getAllMessages());
    }

    private void getMessageById(Context context) throws JsonProcessingException {
        // get information needed from request
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        // calling relevant service layer method
        Message messageRecieved = messageService.grabMessagesById(message_id);
          // check and process the result
        if (messageRecieved != null) {
            context.json(objectMapper.writeValueAsString(messageRecieved));
        } else {
            context.status(200).result(""); 
        }
    }
    
    private  void deleteMessage(Context context) throws  JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        // calling relevant service layer method
        Message deleted = messageService.clearMessage(message_id);

          // check and process the result
        if(deleted != null){
            context.json(objectMapper.writeValueAsString(deleted));
       }
    }

    private void updateMessage(Context context) throws JsonProcessingException {
        Message message = objectMapper.readValue(context.body(), Message.class);
        // get information needed from request
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        // calling relevant service layer method
        Message updateMessage = messageService.amendMessage( message_id, message);
          // Process the result
        if (updateMessage != null) {
            context.json(objectMapper.writeValueAsString(updateMessage));

        } else {
            context.status(400);
        }
    }
    private void getMessageByUser(Context ctx) throws JsonProcessingException {
        // get information needed from request
        int posted_by = Integer.parseInt(ctx.pathParam("account_id"));
        // calling relevant service layer method
        List<Message> messages = messageService.getMessageByAccountId(posted_by);
          // Process the result
        ctx.json(objectMapper.writeValueAsString(messages));

    }
}