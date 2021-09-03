package com.example.xedd.service;

import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.dto.ItemResponseDto;
import com.example.xedd.exception.FileStorageException;
import com.example.xedd.exception.NotFoundException;
import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.Item;
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


    public long addItem(ItemRequestDto itemRequestDto) {

        MultipartFile file =itemRequestDto.getFile();
        String originalFilename = "";
        //dit heb ik hier niet nodig maar misschien wel bij items
        String toPicture = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/upload/")
                .path(file.getOriginalFilename())
                .toUriString();
        //item.setToPicture(toPicture);
        //
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
        newItemToStore.setUsername(itemRequestDto.getUsername());
        if (copyLocation != null ) { newItemToStore.setLocation(copyLocation.toString()); }
        newItemToStore.setToPicture(toPicture);
        newItemToStore.setName(itemRequestDto.getName());
        newItemToStore.setDescription(itemRequestDto.getDescription());
       Item saved = repository.save(newItemToStore);

        return saved.getId();
    }


    @Override
    public void deleteFile(String filename) throws IOException {
        Path deleteLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(filename));
        Files.delete(deleteLocation);
    }



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
    public ItemResponseDto getItemById(long id) {
        Optional<Item> stored = repository.findById(id);

        if (stored.isPresent()) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand("download").toUri();

           ItemResponseDto responseDto = new ItemResponseDto();
            responseDto.setFileName(stored.get().getFileName());
            responseDto.setName(stored.get().getName());
            responseDto.setDescription(stored.get().getDescription());
            responseDto.setMediaType(stored.get().getMediaType());
//            responseDto.setUploadedDate(stored.get().getUploadedDate());
//            responseDto.setUsername(stored.get().getUsername());
            responseDto.setDownloadUri(uri.toString());
            return responseDto;
        }
        else {
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


    @Override
    public boolean itemExistsById(long id) {
        return  repository.existsById(id);
    }

}

