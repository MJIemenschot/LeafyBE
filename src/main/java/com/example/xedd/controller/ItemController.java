package com.example.xedd.controller;

import com.example.xedd.dto.ItemRequestDto;
//import com.example.xedd.dto.PostRequestDto;
import com.example.xedd.model.Item;

import com.example.xedd.repository.ItemRepository;
import com.example.xedd.service.ItemService;
import com.example.xedd.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.net.URI;

@RestController
@CrossOrigin("*")
@RequestMapping(value ="api/v1/items")
public class ItemController {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;



    private List<Item> items = new ArrayList<>();


 //   uit voorbeeld met DTO
    @PostMapping(value = "/add",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
        public ResponseEntity<Object> addItem(ItemRequestDto itemRequestDto) {
        long newId = itemService.addItem(itemRequestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @GetMapping
    public List<Item> getAll(){
        return itemService.getAllItems();
    }

//werkt niet
    @GetMapping("{id}/toPicture")
    public ResponseEntity downloadFile(@PathVariable("id") Long id) {
        Resource resource = itemService.downloadFile(id);
        String fileName = itemService.getItem(id).getToPicture();
        String mediaType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename = \"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(itemService.getItemById(id));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable("id") long id, @RequestBody Item item) {
        itemService.updateItem(id, item);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateItemPartial(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        itemService.partialUpdateItem(id, fields);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}

