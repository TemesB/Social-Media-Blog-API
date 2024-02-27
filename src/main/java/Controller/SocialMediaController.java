package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
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
        private final AccountService accountService;
        private final MessageService messageService;
        private final ObjectMapper objectMapper;
       
        
    
        public SocialMediaController() {
            this.accountService = new AccountService();
            this.messageService = new MessageService();
            this.objectMapper = new ObjectMapper();
         
        }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccount);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{id}", this::getMessageById);
        app.delete("/messages/{id}", this::deleteMessage);
        app.patch("/messages/{id}", this::updateMessage);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccount(Context context) {
        Account account = context.bodyAsClass(Account.class);
        boolean registered = accountService.registerAccount(account);
        if (registered) {
            context.json(account).status(200);
        } else {
            context.status(400);
        }
    }

    private void login(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");
        Account account = accountService.authenticate(username, password);
        if (account != null) {
            context.result("Login successful");
        } else {
            context.result("Invalid username or password");
        }
    }

    private void createMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        messageService.createMessage(message);
        context.result("Message created successfully");
    }

    private void getAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.status(404).result("Message not found");
        }
    }

    private void deleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("id"));
        boolean deleted = messageService.deleteMessage(messageId);
        if (deleted) {
            context.result("Message deleted successfully");
        } else {
            context.status(404).result("Message not found");
        }
    }

    private void updateMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("id"));
        Message message = context.bodyAsClass(Message.class);
        message.setMessage_id(messageId);
        boolean updated = messageService.updateMessage(message);
        if (updated) {
            context.result("Message updated successfully");
        } else {
            context.status(404).result("Message not found");
        }
    }

}