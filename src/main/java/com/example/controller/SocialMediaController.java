package com.example.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.*;
import com.example.exception.ExceptionAndErrorController;
import com.example.exception.InvalidInputException;
import com.example.service.AccountService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/")
public class SocialMediaController {

    private AccountService accountService;
    /**
     * Autowiring {@link AccountService} to inject its dependency.
     * 
     * @param accountService The account service to be injected.
     */
    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService; 
    }

    /**
     * Endpoint for user registration. It accepts an {@link Account} object in the request body.
     *
     * @param account The account to be registered.
     * @return A {@link ResponseEntity} containing the registered account.
     */
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        return ResponseEntity.ok().body(accountService.register(account)); 
    }
    /**
     * Endpoint for user login. It accepts an {@link Account} object in the request body.
     *
     * @param account The account attempting to log in.
     * @return A {@link ResponseEntity} containing the logged-in account.
     * @throws AuthenticationException if authentication fails.
     */
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException{
        return ResponseEntity.ok().body(accountService.login(account));
        
    } 
    /**
     * Retrieves all messages for a specific account.
     *
     * @param account_id The ID of the account.
     * @return A {@link ResponseEntity} containing a list of {@link Message} objects.
     */
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesForAnAccount(@PathVariable Integer account_id){
        return ResponseEntity.ok().body(accountService.getAllMessagesForAnAccount(account_id));

    }

    /**
     * This handles the ExceptionAndErrorController exception and its purpose is to cover when information is conflicting
     * 
     * @param ex the Exception that was thrown
     * @return return a response entity with the error message and a conflice http status
     */
    @ExceptionHandler(ExceptionAndErrorController.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConfigInput(ExceptionAndErrorController ex){return ex.getMessage();}

    /**
     * This handles when the client has not put in information that is valid. For instance, when the password inputed was not greater than 4 characters. 
     * @param ex the exception that was thrown
     * @return
     */
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidInput(InvalidInputException ex){ return ex.getMessage(); }


    /**
     * Handles authentication-related exceptions.
     * This method is invoked when an authentication error occurs, such as incorrect login details.
     *
     * @param ex The {@link AuthenticationException} that was thrown.
     * @return A response entity with the error message and an unauthorized HTTP status.
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthroized(AuthenticationException ex){return ex.getMessage(); }
}
