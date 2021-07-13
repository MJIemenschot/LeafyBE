package com.example.xedd.controller;


import com.example.xedd.model.Message;
import com.example.xedd.model.User;
import com.example.xedd.service.MessageService;
import com.example.xedd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/messages")
public class MessageController {

    @GetMapping("api/v1/messages")
    public Message getMessage () {
        return new Message(1, "leuk", "ghjkghlkjhgfewertyuioiuygfdfghlkjhgfdfghklkjhgfddfghjjfghjghjfgh");
    }

    public List<Message> getMessages(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(2, "ook leuk","nogasdfghjkl.,mnbfdskhgvcxz,lkiuytredsz m,kjuytres" ));
        messages.add(new Message(2, "nog leuker","nogasdflkjhgfdjuytres" ));
        messages.add(new Message(2, "fijn","noglkjhgfdszjjjjjjjjjjjjjjj m,kjuytres" ));
        messages.add(new Message(2, "super","nogasdfghjkl.,mnbfdszxcvbnm,.kjhgfdsz ,lkiuytredsz m,kjuytres" ));
        return messages;
    }
//
//    //private List<Message> messages = new ArrayList<>();
//
//    @Autowired
//    MessageService messageService;
//
//    @GetMapping(value = "")
//    public ResponseEntity<Object> getMessages() {
//        return ResponseEntity.ok().body(messageService.getMessages("", ""));
//    }
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<Object> getMessage(@PathVariable("id") Long id) {
//        return ResponseEntity.ok().body(messageService.getMessageById(id));
//    }
//    /////klopy het volgende wel?
//
//    @PostMapping(value = "")
//    public ResponseEntity<Object> createMessage(@RequestBody Message message) {
//       messageService.createMessage(message);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{message}")
//                .buildAndExpand(message).toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Object> updateMessage(@PathVariable("id") long id, @RequestBody Message message) {
//        messageService.updateMessage(id, message);
//        return ResponseEntity.noContent().build();
//    }
//
////    @DeleteMapping(value = "/{username}")
////    public ResponseEntity<Object> deleteMessage(@PathVariable("id") ) {
////        messageService.deleteMessage(long id);
////        return ResponseEntity.noContent().build();
////    }
}
