package com.example.xedd.repository;

import com.example.xedd.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository <Message, Long>{
    Collection<Message> findAllByTitle(String title);

}
