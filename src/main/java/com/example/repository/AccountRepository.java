package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * Finds an account by its username.
     * This method returns an {@link Optional} of {@link Account}, which will be empty
     * if no account is found with the given username.
     *
     * @param username The username to search for.
     * @return An {@link Optional} containing the found {@link Account} or empty if not found.
     */
    Optional<Account> findAccountByUsername(String username);
    /**
     * Finds an account by its username and password.
     * This method is typically used for authentication purposes. It returns an {@link Optional}
     * of {@link Account}, which will be empty if no account matches the given username and password.
     *
     * @param username The username to search for.
     * @param password The password associated with the username.
     * @return An {@link Optional} containing the found {@link Account} or empty if no match is found.
     */
    Optional<Account> findAccountByUsernameAndPassword(String username, String password); 
}
