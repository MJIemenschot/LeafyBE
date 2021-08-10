package com.example.xedd.controller;


import com.example.xedd.dto.FileUploadResponse;
import com.example.xedd.dto.MessageRequestDto;
import com.example.xedd.dto.MessageResponseDto;
import com.example.xedd.model.Message;
import com.example.xedd.model.User;
import com.example.xedd.service.MessageService;
import com.example.xedd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "api/v1/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/files")
    public ResponseEntity<Object> getFiles() {
        Iterable<Message> files = messageService.getFiles();
        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Object> getMessageInfo(@PathVariable long id) {
        MessageResponseDto response = messageService.getFileById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("files/{id}/fileName")
    public ResponseEntity downloadFile(@PathVariable("id") Long id) {
        Resource resource = messageService.downloadFile(id);
        //String location = messageService.getFileById(id),getLocation();
        //String fileName = messageService.getFileById(id).getFileName();
        String fileName = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileName))
//                //download
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                //display
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "/files",
//            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<Object> uploadMessage(MessageRequestDto messageDto) {
        long newId = messageService.uploadFile(messageDto);
    // uit voorbeeld Peter
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
//   green learner
// /http://localhost:8089/download/abc.jpg
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/download/")
//                .path(fileName)
//                .toUriString();
//
//        String contentType = file.getContentType();
//
//        FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
//
//        return response;
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable long id) {
        messageService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
    //
////    @DeleteMapping(value = "/{username}")
////    public ResponseEntity<Object> deleteMessage(@PathVariable("id") ) {
////        messageService.deleteMessage(long id);
////        return ResponseEntity.noContent().build();
////    }

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
//    /////klopt het volgende wel?
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
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateMessage(@PathVariable("id") long id, @RequestBody Message message) {
        messageService.updateMessage(id, message);
        return ResponseEntity.noContent().build();
    }

}
