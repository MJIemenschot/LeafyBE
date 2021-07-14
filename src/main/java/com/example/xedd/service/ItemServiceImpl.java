package com.example.xedd.service;

import com.example.xedd.exception.FileStorageException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {


//    Path uploads = Paths.get(".\\uploads");
//    private Long id;

    private ItemRepository repository;


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
    //Uit FilestorageserviceImpl
    @Value("${app.upload.dir:${user.home}}")
    private String uploadDirectory;  // relative to root
    private final Path uploads = Paths.get(".\\uploads");

    @Override
    public void uploadFile(MultipartFile toPicture) {
        try {
            Path copyLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(toPicture.getOriginalFilename()));
            Files.copy(toPicture.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            Item item = new Item();
            item.setDescription(toPicture.getName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + toPicture.getOriginalFilename() + ". Please try again.");
        }
    }

//    @Override
//    public void deleteFile(String filename) throws IOException {
//        Path deleteLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(filename));
//        Files.delete(deleteLocation);
//    }

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
        existingItem.setToPicture(item.getToPicture());

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
                case "toPicture":
                    item.setToPicture((String) fields.get(field));

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

