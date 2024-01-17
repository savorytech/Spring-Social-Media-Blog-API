package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

import java.util.Collection;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Retrieves all messages posted by a specific account.
     * 
     * @param account_id The ID of the account whose messages are to be retrieved.
     * @return A {@link Collection} of {@link Message} entities posted by the specified account.
     */
    @Query("select m from Message m where m.posted_by = ?1")
    Collection<Message> findAllMessagesByPostedBy(Integer account_id);
}
