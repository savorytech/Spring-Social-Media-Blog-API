package com.example.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ExceptionAndErrorController;
import com.example.exception.InvalidInputException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import com.example.entity.*;
@Service
public class AccountService {
    private AccountRepository accountRepository; 
    private MessageRepository messageRepository;
    /**
     * Autowires {@link AccountRepository} and {@link MessageRepository} to inject their dependencies.
     *
     * @param accountRepository The account repository to be injected.
     * @param messageRepository The message repository to be injected.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }
     /**
     * Registers a new account.
     * Validates the account details and saves the account using {@link AccountRepository}.
     * Throws {@link InvalidInputException} for invalid inputs.
     *
     * @param account The {@link Account} to be registered.
     * @return The registered {@link Account}.
     * @throws InvalidInputException if the username is blank or the password is too short.
     */
    public Account register(Account account){
        int lengthOfUsername = account.getUsername().replace("//s", "").length(); 
        if(lengthOfUsername == 0)
            throw new InvalidInputException("The Username should not be blank"); 

        if(account.getPassword().length() < 4)
            throw new InvalidInputException("The password should have 4 or more characters"); 
        
        accountRepository.findAccountByUsername(account.getUsername()).ifPresent(existingAccount -> {throw new ExceptionAndErrorController("no good ");});

        return accountRepository.save(account); 
    }

    /**
     * Authenticates an account for login.
     * Validates the account credentials and returns the account details.
     * Throws {@link AuthenticationException} if authentication fails.
     *
     * @param account The {@link Account} attempting to log in.
     * @return The authenticated {@link Account}.
     * @throws AuthenticationException if the account credentials are incorrect.
     */
    public Account login(Account account) throws AuthenticationException{
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword()).orElseThrow(() -> new AuthenticationException());
    }
    
    /**
     * Retrieves all messages for a specific account.
     *
     * @param account_id The ID of the account whose messages are to be retrieved.
     * @return A list of {@link Message} entities posted by the specified account.
     */
    public List<Message> getAllMessagesForAnAccount(Integer account_id){
        return (List<Message>) messageRepository.findAllMessagesByPostedBy(account_id); 
    }


}
