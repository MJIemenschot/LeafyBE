package com.example.xedd.service;

import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.model.Item;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemService {

    Item addItemToUser(Long id, String username);
    List<Item> getAllItems();
    Collection<Item> getItems(String name);
    Item getItem(Long id);
    Optional<Item> getItemById(long id);
    void deleteItem(long id);
    long createItem(Item item);
    void updateItem(long id, Item item);
    Resource downloadFile(Long id);
    void partialUpdateItem(long id, Map<String, String> fields);
    //long uploadFile(ItemRequestDto itemDto);
    //uit FilestorageService
    void uploadFile(MultipartFile toPicture);
    void deleteFile(String filename) throws IOException;
    List<Object> getAllSeeds();
    //List<Object> getAllEnts();
    //List<Object> getPlants();


    public boolean itemExistsById(long id);
}
