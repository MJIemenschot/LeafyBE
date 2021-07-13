package com.example.xedd.service;

import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.model.Item;
import org.springframework.core.io.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemService {
    List<Item> getAllItems();
    long createItem(Item item);
    Collection<Item> getItems(String name);
    //Item getItem(Long id);
    //long uploadFile(ItemRequestDto itemDto);
    Optional<Item> getItemById(long id);
    void updateItem(long id, Item item);
    void partialUpdateItem(long id, Map<String, String> fields);
    void deleteItem(long id);
    Resource downloadFile(Long id);
//    List<Object> getAllSeeds();

    public boolean itemExistsById(long id);



}
