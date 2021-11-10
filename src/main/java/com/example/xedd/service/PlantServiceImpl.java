package com.example.xedd.service;

import com.example.xedd.dto.PlantRequestDto;
import com.example.xedd.dto.PlantResponseDto;
import com.example.xedd.exception.FileStorageException;
import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.*;
import com.example.xedd.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class PlantServiceImpl implements PlantService {
    @Value("${app.upload.dir:${user.home}}")
    private String uploadDirectory;  // relative to root
    private final Path uploads = Paths.get("uploads");

    @Autowired
    private PlantRepository repository;

    public PlantServiceImpl(PlantRepository repository) {
    }


    @Override
    public void init() {
        try {
            Files.createDirectory(uploads);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public List<Plant> getPlants() {
        return repository.findAll();
    }

    @Override
    public List<Plant> findByName(String query) {
        return repository.findPlantsByNameContainingIgnoreCase(query);
    }
    @Override
    public List<Plant> findByLatin(String query) {
        return repository.findPlantsByLatinNameContainingIgnoreCase(query);
    }

    @Override
    public PlantResponseDto getPlantById(long id) {
        Date createDate = new Date();

        Optional<Plant> stored = repository.findById(id);

        if (stored.isPresent()) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand("download").toUri();
            PlantResponseDto responseDto = new PlantResponseDto();
            responseDto.setId(stored.get().getId());
            responseDto.setFileName(stored.get().getFileName());
            responseDto.setLatinName(stored.get().getLatinName());
            responseDto.setName(stored.get().getName());
            responseDto.setDescription(stored.get().getDescription());
            responseDto.setCare(stored.get().getCare());
            responseDto.setPotting(stored.get().getPotting());
            responseDto.setMediaType(stored.get().getMediaType());
            responseDto.setDownloadUri(uri.toString());
            responseDto.setDifficulty(stored.get().getDifficulty());
            responseDto.setLight(stored.get().getLight());
            responseDto.setWatering(stored.get().getWatering());
            responseDto.setFood(stored.get().getFood());
            responseDto.setUploadedDate(createDate);
            responseDto.setUploadedByUsername(stored.get().getUploadedByUsername());
            responseDto.setLocation(stored.get().getLocation());
            return responseDto;
        }
        else {
            throw new RecordNotFoundException();
        }
    }
    //Als het aantal planten toeneemt kan deze service geimplementeerd worden(goedgekeurd door postman)
    @Override
    public Page<Plant> findAllPlants(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public boolean plantExists(String name){return repository.existsPlantByName(name);}



    public long addPlant(PlantRequestDto plantDto) {
        if (plantExists(plantDto.getName())) {
            throw new RuntimeException("Deze plant bestaat al");
        }
        Date createDate = new Date();
        MultipartFile file = plantDto.getFile();

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        Path copyLocation = this.uploads.resolve(file.getOriginalFilename());

        try {
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!");
        }
        Plant newPlantToStore = new Plant();
        newPlantToStore.setFileName(originalFilename);
        newPlantToStore.setLocation(copyLocation.toString());
        newPlantToStore.setName(plantDto.getName());
        newPlantToStore.setLatinName(plantDto.getLatinName());
        newPlantToStore.setDescription(plantDto.getDescription());
        newPlantToStore.setCare(plantDto.getCare());
        newPlantToStore.setPotting(plantDto.getPotting());
        newPlantToStore.setDifficulty(plantDto.getDifficulty());
        newPlantToStore.setWatering(plantDto.getWatering());
        newPlantToStore.setFood(plantDto.getFood());
        newPlantToStore.setLight(plantDto.getLight());
        newPlantToStore.setUploadedDate(createDate);
        //newPlantToStore.setUploadedByUsername(plantDto.getUploadedByUserName);

        Plant saved = repository.save(newPlantToStore);

        return saved.getId();
    }


    public void updatePlant(PlantRequestDto plantDto) {
        MultipartFile file = plantDto.getFile();
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        Path copyLocation = this.uploads.resolve(file.getOriginalFilename());

        try {
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!");
        }
        Optional<Plant> stored = repository.findById(plantDto.getId());
        if(stored.isPresent()) {
            var plantToUpdate = stored.get();
            plantToUpdate.setFileName(originalFilename);
            plantToUpdate.setLocation(copyLocation.toString());
            plantToUpdate.setName(plantDto.getName());
            plantToUpdate.setLatinName(plantDto.getLatinName());
            plantToUpdate.setDescription(plantDto.getDescription());
            plantToUpdate.setCare(plantDto.getCare());
            plantToUpdate.setPotting(plantDto.getPotting());
            plantToUpdate.setDifficulty(plantDto.getDifficulty());
            plantToUpdate.setWatering(plantDto.getWatering());
            plantToUpdate.setFood(plantDto.getFood());
            plantToUpdate.setLight(plantDto.getLight());
            //plantToUpdate.setUploadedDate(createDate);
            //newPlantToStore.setUploadedByUsername(plantDto.getUploadedByUserName);

            repository.save(plantToUpdate);
        }
    }
    public void partialUpdatePlant(PlantRequestDto plantDto) {
        Optional<Plant> stored = repository.findById(plantDto.getId());
        if(stored.isPresent()) {
            var nPlant = stored.get();
            nPlant.setName(plantDto.getName());
            nPlant.setLatinName(plantDto.getLatinName());
            nPlant.setDescription(plantDto.getDescription());
            nPlant.setCare(plantDto.getCare());
            nPlant.setPotting(plantDto.getPotting());
            nPlant.setDifficulty(plantDto.getDifficulty());
            nPlant.setWatering(plantDto.getWatering());
            nPlant.setLight(plantDto.getLight());
            nPlant.setFood(plantDto.getFood());
            repository.save(nPlant);
        }
    }

    //upload image to plant

    @Override
    public void uploadImage(PlantRequestDto plantDto) {
        MultipartFile file = plantDto.getFile();
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        Path copyLocation = this.uploads.resolve(file.getOriginalFilename());

        try {
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!");
        }
        Optional<Plant> optionalPlant = repository.findById(plantDto.getId());
        if (optionalPlant.isPresent()) {
            var plant = optionalPlant.get();
            plant.setFileName(originalFilename);
            plant.setLocation(copyLocation.toString());

            repository.save(plant);
        } else {
            throw new RecordNotFoundException();
        }
    }
    //upload een file los
    @Override
    public String uploadFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.uploads.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation,StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch(IOException ex) {
            throw new FileStorageException("Could not store file"+fileName + ". Please try again!",ex);
        }
    }


    @Override
    public void deletePlant(long id) {
        Optional<Plant> stored = repository.findById(id);

        if (stored.isPresent()) {
            String filename = stored.get().getFileName();
            Path location = this.uploads.resolve(filename);
            try {
                Files.deleteIfExists(location);
            }
            catch (IOException ex) {
                throw new RuntimeException("File not found");
            }

            repository.deleteById(id);
        }
        else {
            throw new RecordNotFoundException();
        }
    }


    @Override
    public Resource downloadFile(long id) {
        Optional<Plant> stored = repository.findById(id);

        if (stored.isPresent()) {
            String filename = stored.get().getFileName();
            Path path = this.uploads.resolve(filename);

            Resource resource = null;
            try {
                resource = new UrlResource(path .toUri());
                return resource;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new RecordNotFoundException();
        }

        return null;
    }
    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }


    @Override
    public List<Plant> findAllByDifficulty(Difficulty difficulty) {
        return repository.findAllByDifficulty(difficulty);
    }

    @Override
    public List<Plant> findAllByLight(Light light) {
        return repository.findAllByLight(light);
    }

    @Override
    public List<Plant> findAllByWatering(Watering watering) {
        return repository.findAllByWatering(watering);
    }

    @Override
    public List<Plant> findAllByFood(Food food) {
        return repository.findAllByFood(food);
    }

}
