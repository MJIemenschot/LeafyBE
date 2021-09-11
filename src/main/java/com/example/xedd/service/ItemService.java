package com.example.xedd.service;

//import com.example.xedd.dto.ItemRequestDto;
//import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.model.Difficulty;
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

    List<Item> getAllItems();
    Collection<Item>findAllByDifficulty(Difficulty difficulty);
    Collection<Item> getItems(String name);
    Item getItem(Long id);
    ItemResponseDto getItemById(long id);
    void deleteItem(long id);
    long addItem(ItemRequestDto itemRequestDto);
    void updateItem(long id, Item item);
    //void updateItem(long id, ItemRequestDto itemRequestDto);
    //Resource downloadFile(Long id);
    public void partialUpdateItem(long id, ItemRequestDto itemRequestDto);
    //void partialUpdateItem(long id, Map<String, String> fields);
    void deleteFile(String filename) throws IOException;



    public boolean itemExistsById(long id);
}
