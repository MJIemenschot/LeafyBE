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
    List<Item> getAllItems();
    long createItem(Item item);
    Collection<Item> getItems(String name);
    //Item getItem(Long id);

    Optional<Item> getItemById(long id);
    void updateItem(long id, Item item);
    void partialUpdateItem(long id, Map<String, String> fields);
    void deleteItem(long id);
    Resource downloadFile(Long id);
    //long uploadFile(ItemRequestDto itemDto);

    //uit FilestorageService
    void uploadFile(MultipartFile toPicture);
//    void deleteFile(String filename) throws IOException;


    public boolean itemExistsById(long id);
}
