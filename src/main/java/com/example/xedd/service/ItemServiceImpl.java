package com.example.xedd.service;

import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.dto.MessageRequestDto;
import com.example.xedd.exception.FileStorageException;
import com.example.xedd.exception.NotFoundException;
import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.Item;
import com.example.xedd.model.Message;
import com.example.xedd.repository.ItemRepository;
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
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    //Uit FilestorageserviceImpl
    @Value("${app.upload.dir:...}")
    private String uploadDirectory;  // relative to root
    Path uploads = Paths.get(".\\upload");

    @Override
    public void init() {
        try {
            Files.createDirectory(uploads);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    //    Path uploads = Paths.get(".\\uploads");
//    private Long id;

    @Autowired
    private ItemRepository repository;

    @Override
    public List<Item> getAllItems() {
        return repository.findAll();
    }



//    @Override
//    public Item saveItem(Item item) {
//       return repository.save(item);
//    };


    public long addItem(ItemRequestDto itemRequestDto) {

        MultipartFile file =itemRequestDto.getFile();
        String originalFilename = "";
        Path copyLocation = null;
        if (file != null) {
            originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            copyLocation = this.uploads.resolve(file.getOriginalFilename());
            try {
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!");
            }
        }
        Item newFileToStore = new Item();
        newFileToStore.setFileName(originalFilename);
        //newFileToStore.setUploadedByUsername(messageRequestDto.getUploadedByUsername());
        if (copyLocation != null ) { newFileToStore.setLocation(copyLocation.toString()); }
        newFileToStore.setName(itemRequestDto.getName());
        newFileToStore.setDescription(itemRequestDto.getDescription());
       Item saved = repository.save(newFileToStore);

        return saved.getId();
    }


//    @Override
//    public Item addItemToUser(Long id, String username) {
//        if (!repository.existsById(id))throw new NotFoundException();
//
//        Item item = repository.getById(id);
//        Item newItem = new Item();
//
//        newItem.setUser(username);
//        newItem.setSeed(false);
//        newItem.setEnt(false);
//        newItem.setPlant(false);
//        newItem.setName(item.getName());
//        newItem.setDescription(item.getDescription());
//        newItem.setToPicture(item.getToPicture());
//
//        return repository.save(newItem);
//
//    }

//    @Override
//    public long createItem(Item item) {
//        Item newItem = repository.save(item);
//        return newItem.getId();
//    }

    //Zaaim
    //public saveItem( String name, String description, String toPicture){
     //   Item item = new Item();
        //String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if(fileName.contains("..")){
//            System.out.println("not a valid file");
//        }
//        try {
//            item.setToPicture(Base64.getEncoder().encodeToString(file.getBytes()));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
       // return repository.save(item);



//    }
//    @Override
//    public void uploadFile(MultipartFile toPicture) {
//        try {
//            Path copyLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(toPicture.getOriginalFilename()));
//            Files.copy(toPicture.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
////            Item item = new Item();
////            item.setDescription(toPicture.getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new FileStorageException("Could not store file " + toPicture.getOriginalFilename() + ". Please try again.");
//        }
//    }

    @Override
    public void deleteFile(String filename) throws IOException {
        Path deleteLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(filename));
        Files.delete(deleteLocation);
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
    @Override
    public Item getItem(Long id) {
        return repository.getById(id);
    }
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


    @Override
    public boolean itemExistsById(long id) {
        return  repository.existsById(id);
    }

//    @Override
//    public List<Object> getAllSeeds() {
//        List<Item> itemList = repository.findAll();
//        List<Object> seeds = new ArrayList<>();
//        for (int i = 0; i < itemList.size(); i++) {
//            if (itemList.get(i).isSeed()) seeds.add(itemList.get(i));
//        }
//        return seeds;
//    }


}

