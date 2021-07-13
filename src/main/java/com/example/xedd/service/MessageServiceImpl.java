package com.example.xedd.service;

import com.example.xedd.exception.NotFoundException;
import com.example.xedd.model.Message;
import com.example.xedd.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public long createMessage(Message message) {
        //return messageRepository.save(message);
        Message newMessage = messageRepository.save(message);
        return newMessage.getId();
    }

    @Override
    public void updateMessage(long id, Message message) {

    }

    @Override
    public void partialUpdateMessage(long id, Map<String, String> fields) {

    }

    @Override
    public void deleteMessage(long id) {
        if (!messageRepository.existsById(id)) { throw new NotFoundException(); }
        messageRepository.deleteById(id);

    }

    @Override
    public Collection<Message> getMessages(String title, String content) {
        return null;
    }

    @Override
    public Message getMessageById(long id) {
        if (!messageRepository.existsById(id)) { throw new NotFoundException(); }
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public boolean messageExistsById(long id) {
        return false;
    }

   
}
