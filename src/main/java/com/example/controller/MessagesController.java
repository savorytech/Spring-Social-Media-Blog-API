package com.example.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.*;
import com.example.entity.*;
import com.example.exception.InvalidInputException;

import java.util.List; 
/**
 * The {@code MessagesController} class manages API requests related to message operations.
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {
    private MessageService messageService; 

    /**
     * Autowires {@link MessageService} and {@link AccountService} to inject their dependencies.
     * 
     * @param messageService The message service to be injected.
     */
    @Autowired
    public MessagesController(MessageService messageService){
        this.messageService = messageService;
    }
    /**
     * Retrieves all messages.
     *
     * @return A {@link ResponseEntity} containing a list of {@link Message} objects.
     */
    @GetMapping()
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok().body(messageService.getAllMessages()); 
    }
    /**
     * Posts a new message.
     *
     * @param message The {@link Message} object to be posted.
     * @return A {@link ResponseEntity} containing the posted message.
     */
    @PostMapping()
    public ResponseEntity<Message> postAMessage(@RequestBody Message message){
        return ResponseEntity.ok().body(messageService.postAMessage(message));
    }
    /**
     * Retrieves a message by its ID.
     *
     * @param msg_id The ID of the message to be retrieved.
     * @return A {@link ResponseEntity} containing the requested {@link Message}.
     */
    @GetMapping("/{msg_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer msg_id){
        return ResponseEntity.ok().body(messageService.getMessageByid(msg_id));
    }
    /**
     * Deletes a message by its ID.
     *
     * @param msg_id The ID of the message to be deleted.
     * @return A {@link ResponseEntity} containing the ID of the deleted message.
     */
    @DeleteMapping("/{msg_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer msg_id){
        return ResponseEntity.ok().body(messageService.deleteMessageById(msg_id));

    }

    /**
     * Updates a message by its ID.
     * 
     * @param msg_id The ID of the message to be updated.
     * @param message The {@link Message} object containing the updated information.
     * @return A {@link ResponseEntity} containing the ID of the updated message.
     */
    @PatchMapping("/{msg_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer msg_id, @RequestBody Message message){
        return ResponseEntity.ok().body(messageService.updateMessageById(msg_id, message));

    }

    /**
     * Handles {@link InvalidInputException} thrown within the controller.
     * This method is invoked when an invalid input is provided to any of the endpoints.
     *
     * @param ex The {@link InvalidInputException} that was thrown.
     * @return A response entity with the error message and a bad request HTTP status.
     */
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidInput(InvalidInputException ex){ return ex.getMessage(); }
}
