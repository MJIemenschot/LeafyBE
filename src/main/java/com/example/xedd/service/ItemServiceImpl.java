package com.example.xedd.service;

import com.example.xedd.exception.NotFoundException;
import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.Item;
import com.example.xedd.repository.ItemRepository;
import com.example.xedd.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {


    Path uploads = Paths.get("./uploads");

    private ItemRepository repository;

//    Path uploads = Paths.get(".\\uploads");
//    private Long id;

    @Autowired ItemServiceImpl(ItemRepository itemRepository) {
        this.repository = itemRepository;
    }

    @Override
    public List<Item> getAllItems() {
        return repository.findAll();
    }
    @Override
    public long createItem(Item item) {
        Item newItem = repository.save(item);
        return newItem.getId();
    }

    public Collection<Item> getItems(String name) {
        if (name.isEmpty()) {
            return repository.findAll();
        }
        else {
            return repository.findAllByName(name);
        }
    }
//    @Override
//    public Item getItem(Long id) {
//        return repository.getItemById(id);
//    }
    @Override
    public Optional<Item> getItemById(long id) {
       if (!repository.existsById(id)) throw new RecordNotFoundException();
        return repository.findById(id);
    }
    @Override
    public void updateItem(long id, Item item) {
        if (!repository.existsById(id)) throw new RecordNotFoundException();
        Item existingItem = repository.findById(id).get();
        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());

        repository.save(existingItem);

    }

    @Override
    public void partialUpdateItem(long id, Map<String, String> fields) {
        if (!repository.existsById(id)) throw new RecordNotFoundException();
        Item item = repository.findById(id).get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "name":
                    item.setName((String) fields.get(field));
                    break;
                case "description":
                    item.setDescription((String) fields.get(field));
                    break;

            }
        }
        repository.save(item);

    }
    @Override
    public void deleteItem(long id) {
        if (!repository.existsById(id)) throw new RecordNotFoundException();
        repository.deleteById(id);

    }
    public Resource downloadFile(Long id) {
        Optional<Item> stored = repository.findById(id);

        if (stored.isPresent()) {
            String fileName = stored.get().getToPicture();
            Path path = this.uploads.resolve(fileName);

            Resource resource = null;

            try {
                resource = new UrlResource(path.toUri());
                return resource;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotFoundException();
        }
        return null;
    }
//    @Override
//    public Collection<Item> getItems(String name, String description) {
//        return itemRepository.findAll();
//    }
//    public Collection<Item> getItems(String name, String description) {
//        if (!name.isEmpty()) {
//            if (!name.isEmpty()) {
//                return itemRepository.findAllByNameAndDescription(name, description);
//            }
//            else {
//                return itemRepository.findAllByName(name);
//            }
//        }
//        else {
//            if (!description.isEmpty()) {
//                return itemRepository.findAllByDescription(description);
//            }
//            else {
//                return itemRepository.findAll();
//            }
//        }

//
    @Override
    public boolean itemExistsById(long id) {
        return  repository.existsById(id);
    }

//    @Override
//    public List<Object> getAllSeeds() {
//        List<Item> itemList = repository.findAllSeeds();
//        List<Object> seeds = new ArrayList<>();
//        for (int i = 0; i < itemList.size(); i++) {
//            if (itemList.get(i).isSeed()) seeds.add(itemList.get(i));
//        }
//        return seeds;
//    }
}

