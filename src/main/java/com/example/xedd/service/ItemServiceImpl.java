package com.example.xedd.service;

import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.exception.FileStorageException;
import com.example.xedd.exception.NotFoundException;
import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.*;
import com.example.xedd.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
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

    @Autowired
    private ItemRepository repository;

    @Override
    public List<Item> getAllItems() {
        return repository.findAll();
    }

    @Override
    public Collection<Item> findAllByDifficulty(Difficulty difficulty) {
        return repository.findAllByDifficulty(difficulty);
    }

    @Override
    public Collection<Item> findAllByLight(Light light) {
        return repository.findAllByLight(light);
    }

    @Override
    public Collection<Item> findAllByWatering(Watering watering) {
        return repository.findAllByWatering(watering);
    }

    @Override
    public Collection<Item> findAllByFood(Food food) {
        return repository.findAllByFood(food);
    }

    public long addItem(ItemRequestDto itemRequestDto) {
        MultipartFile file = itemRequestDto.getFile();
        String originalFilename = "";
        String toPicture = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/upload/")
                .path(file.getOriginalFilename())
                .toUriString();
        //
        Date createDate = new Date();
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
        Item newItemToStore = new Item();
        newItemToStore.setFileName(originalFilename);
        newItemToStore.setDifficulty(itemRequestDto.getDifficulty());
        newItemToStore.setLight(itemRequestDto.getLight());
        newItemToStore.setWatering(itemRequestDto.getWatering());
        newItemToStore.setFood(itemRequestDto.getFood());
        newItemToStore.setUsername(itemRequestDto.getUsername());
//        if (copyLocation != null ) { newItemToStore.setLocation(copyLocation.toString()); }
        newItemToStore.setToPicture(toPicture);
        newItemToStore.setName(itemRequestDto.getName());
        newItemToStore.setDescription(itemRequestDto.getDescription());
        newItemToStore.setUploadedDate(createDate);
        Item saved = repository.save(newItemToStore);

        return saved.getId();
    }

    public Collection<Item> getAllByName(String name) {
        if (name.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.findAllByName(name);
        }
    }

    @Override
    public Item getItem(Long id) {
        return repository.getById(id);
    }

    @Override
    public ItemResponseDto getItemById(long id) {
        Optional<Item> stored = repository.findById(id);

        if (stored.isPresent()) {
//            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                    .buildAndExpand("download").toUri();
            ItemResponseDto responseDto = new ItemResponseDto();
            responseDto.setId(stored.get().getId());
            responseDto.setFileName(stored.get().getFileName());
            responseDto.setName(stored.get().getName());
            responseDto.setDescription(stored.get().getDescription());
            responseDto.setToPicture(stored.get().getToPicture());
            responseDto.setMediaType(stored.get().getMediaType());
            responseDto.setUploadedDate(stored.get().getUploadedDate());
            responseDto.setDifficulty(stored.get().getDifficulty());
            responseDto.setLight(stored.get().getLight());
            responseDto.setWatering(stored.get().getWatering());
            responseDto.setFood(stored.get().getFood());
            responseDto.setUsername(stored.get().getUsername());
            //responseDto.setDownloadUri(uri.toString());
            return responseDto;
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void updateItem(long id, Item item) {
        if (!repository.existsById(id)) throw new RecordNotFoundException();
        Item existingItem = repository.findById(id).get();
        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setToPicture(item.getToPicture());
        existingItem.setDifficulty(item.getDifficulty());
        existingItem.setLight(item.getLight());
        existingItem.setWatering(item.getWatering());
        existingItem.setFood(item.getFood());
        existingItem.setUploadedDate(new Date());

        repository.save(existingItem);
    }
public void updateItem(ItemRequestDto itemRequestDto) {
    MultipartFile file = itemRequestDto.getFile();
    String originalFilename = "";
    String toPicture = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/upload/")
            .path(file.getOriginalFilename())
            .toUriString();
    //
    Date createDate = new Date();
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
    Optional<Item> stored = repository.findById(itemRequestDto.getId());
    if(stored.isPresent()) {
        var ItemToStore = stored.get();
        ItemToStore.setFileName(originalFilename);
        ItemToStore.setDifficulty(itemRequestDto.getDifficulty());
        ItemToStore.setLight(itemRequestDto.getLight());
        ItemToStore.setWatering(itemRequestDto.getWatering());
        ItemToStore.setFood(itemRequestDto.getFood());
//        if (copyLocation != null ) { newItemToStore.setLocation(copyLocation.toString()); }
        //ItemToStore.setUsername(itemRequestDto.getUserName());
        ItemToStore.setToPicture(toPicture);
        ItemToStore.setName(itemRequestDto.getName());
        ItemToStore.setDescription(itemRequestDto.getDescription());
        repository.save(ItemToStore);
    }

}


    @Override
    public void deleteItem(long id) {
        if (!repository.existsById(id)) throw new RecordNotFoundException();
        repository.deleteById(id);
    }
    @Override
    public void deleteFile(String filename) throws IOException {
        Path deleteLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(filename));
        Files.delete(deleteLocation);
    }
//    @Override
//    public void deleteItemWithFile(long id, String filename) {
//        if (!repository.existsById(id)) throw new RecordNotFoundException();
//        repository.deleteById(id);
//        Path deleteLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(filename));
//        try {
//            Files.delete(deleteLocation);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void deleteItemWithFile(long id, String filename) {
        Optional<Item> stored = repository.findById(id);
        if (stored.isPresent()) {
            //String fileName = stored.get().getToPicture();
            Path deleteLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(filename));
            try {
                repository.deleteById(id);
                Files.delete(deleteLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotFoundException();
        }
      //  return null;

    }

    @Override
    public boolean itemExistsById(long id) {
        return  repository.existsById(id);
    }

//    @Override
//    public void deleteItem(long id) {
//
//        //bestandsnaam uit database
//    }



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


}

