package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.*;
import com.example.exception.InvalidInputException;

import java.util.List;
import java.util.Optional; 
@Service
public class MessageService {
    private MessageRepository messageRepository; 
    private AccountRepository accountRepository; 
    /**
     * Autowires {@link MessageRepository} and {@link AccountRepository} to inject their dependencies.
     *
     * @param messageRepository The message repository to be injected.
     * @param accountRepository The account repository to be injected.
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository; 
        this.accountRepository = accountRepository; 
    }
    /**
     * Retrieves all messages.
     *
     * @return A list of all {@link Message} entities.
     */
    public List<Message> getAllMessages(){
        return (List<Message>) messageRepository.findAll();
    }
    /**
     * Posts a new message.
     * Validates the message content and saves the message using {@link MessageRepository}.
     * Throws {@link InvalidInputException} for invalid inputs.
     *
     * @param message The {@link Message} to be posted.
     * @return The posted {@link Message}.
     * @throws InvalidInputException if the message is blank or exceeds character limits.
     */
    public Message postAMessage(Message message){
        if(message.getMessage_text().replace("//s", "").length() == 0 )
            throw new InvalidInputException("the message shouldn't be blank"); 

        if(message.getMessage_text().length() > 255)
            throw new InvalidInputException("the message should not be greater than 255 characters");
        
        accountRepository.findById(message.getPosted_by()).orElseThrow(() -> new InvalidInputException("The user has to exist"));
        return messageRepository.save(message);

    }
    /**
     * Retrieves a message by its ID.
     *
     * @param msg_id The ID of the message to be retrieved.
     * @return The {@link Message} entity or null if not found.
     */
    public Message getMessageByid(Integer msg_id){
        Optional<Message>  m = messageRepository.findById(msg_id);
        if(m.isPresent())
            return m.get(); 

        return null; 
    }
    /**
     * Deletes a message by its ID.
     *
     * @param msg_id The ID of the message to be deleted.
     * @return 1 if deletion is successful, null otherwise.
     */
    public Integer deleteMessageById(Integer msg_id){
        Optional<Message> m = messageRepository.findById(msg_id); 
        if(m.isPresent()){
            messageRepository.delete(m.get());
            return 1;
        }
        return null; 
    }

    /**
     * Updates the text of a message by its ID.
     * Validates the updated message content and updates the message using {@link MessageRepository}.
     * Throws {@link InvalidInputException} for invalid inputs or if the message is not found.
     *
     * @param msg_id  The ID of the message to be updated.
     * @param message The updated {@link Message} content.
     * @return 1 if the update is successful.
     * @throws InvalidInputException if the message is blank, exceeds character limits, or is not found.
     */
    public Integer updateMessageById(Integer msg_id, Message message){
        if(message.getMessage_text().replace("//s", "").length() == 0 )
            throw new InvalidInputException("the message Text provided was blank");
        if(message.getMessage_text().length() > 255)
            throw new InvalidInputException("the message should not be greater than 255 characters"); 
        Message m = messageRepository.findById(msg_id).orElseThrow(() -> new InvalidInputException("the message was not found"));
        m.setMessage_text(message.getMessage_text());
        messageRepository.save(m); 
        return 1;
    }

}
