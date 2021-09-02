package com.example.xedd.service;

//import com.example.xedd.dto.ItemRequestDto;
//import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.model.Item;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemService {
    void init();

    //Zaaim
   // public saveItem(MultipartFile file, String name, String description, String toPicture);

    //Item addItemToUser(Long id, String username);
    List<Item> getAllItems();
    Collection<Item> getItems(String name);
    Item getItem(Long id);
    ItemResponseDto getItemById(long id);
    void deleteItem(long id);

    //long createItem(Item item);
    //Item saveItem(Item item);
    //Item addItem(Item item, MultipartFile toPicture);
    long addItem(ItemRequestDto itemRequestDto);

    void updateItem(long id, Item item);
    Resource downloadFile(Long id);
    void partialUpdateItem(long id, Map<String, String> fields);
    //public uploadFile(ItemResponseDto itemDto);
    //uit FilestorageService
   // uploadFile(MultipartFile toPicture);
    void deleteFile(String filename) throws IOException;



    public boolean itemExistsById(long id);
}
