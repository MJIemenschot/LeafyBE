package com.example.xedd.service;

//import com.example.xedd.dto.ItemRequestDto;
//import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.model.*;
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
    Collection<Item> getAllByName(String name);
    Item getItem(Long id);
    ItemResponseDto getItemById(long id);
    void deleteItem(long id);
    void deleteItemWithFile(long id, String filename);

    long addItem(ItemRequestDto itemRequestDto);
    void updateItem(long id, Item item);
    Collection<Item>findAllByDifficulty(Difficulty difficulty);
    Collection<Item>findAllByLight(Light light);
    Collection<Item>findAllByWatering(Watering watering);
    Collection<Item>findAllByFood(Food food);
    //Resource downloadFile(Long id);
    public void updateItem(ItemRequestDto itemRequestDto);
    //public void updateItemBy(long id, Item item, MultipartFile file );
    //void partialUpdateItem(long id, Map<String, String> fields);
    void deleteFile(String filename) throws IOException;
    public boolean itemExistsById(long id);
}
