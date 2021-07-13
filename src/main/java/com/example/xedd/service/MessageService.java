package com.example.xedd.service;

import com.example.xedd.model.Message;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface MessageService {
    long createMessage(Message message);
    void updateMessage(long id, Message message);
    void partialUpdateMessage(long id, Map<String, String> fields);
    void deleteMessage(long id);
    Collection<Message> getMessages(String title, String content);
    Message getMessageById(long id);

    public boolean messageExistsById(long id);
}
