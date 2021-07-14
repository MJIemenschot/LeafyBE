package com.example.xedd.service;

import com.example.xedd.dto.MessageRequestDto;
import com.example.xedd.dto.MessageResponseDto;
import com.example.xedd.model.Message;
import org.springframework.core.io.Resource;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface MessageService {
    void init();
    Iterable<Message> getFiles();
    MessageResponseDto getFileById(long id);
    boolean fileExistsById(long id);
    long uploadFile(MessageRequestDto method1Dto);
    void deleteFile(long id);
    Resource downloadFile(long id);
//    long createMessage(Message message);
//    void updateMessage(long id, Message message);
//    void partialUpdateMessage(long id, Map<String, String> fields);
//    void deleteMessage(long id);
//    Collection<Message> getMessages(String title, String content);
//    Message getMessageById(long id);
//
//    public boolean messageExistsById(long id);
}
