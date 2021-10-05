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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlantServiceImpl implements PlantService {
    @Value("${app.upload.dir:${user.home}}")
    private String uploadDirectory;  // relative to root
    private final Path uploads = Paths.get("uploads");

    @Autowired
    private PlantRepository repository;

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
            responseDto.setMediaType(stored.get().getMediaType());
            responseDto.setDownloadUri(uri.toString());
            responseDto.setDifficulty(stored.get().getDifficulty());
            responseDto.setLight(stored.get().getLight());
            responseDto.setWatering(stored.get().getWatering());
            responseDto.setFood(stored.get().getFood());
            responseDto.setUploadedDate(createDate);
            responseDto.setUploadedByUsername(stored.get().getUploadedByUsername());
            return responseDto;
        }
        else {
            throw new RecordNotFoundException();
        }
    }
//    @Override
//    public Page<Plant> getAll(Pageable pageable){
//        return repository.getAll(pageable);
//    }

    public long addPlant(PlantRequestDto plantDto) {
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
            plantToUpdate.setDifficulty(plantDto.getDifficulty());
            plantToUpdate.setWatering(plantDto.getWatering());
            plantToUpdate.setFood(plantDto.getFood());
            plantToUpdate.setLight(plantDto.getLight());
            //plantToUpdate.setUploadedDate(createDate);
            //newPlantToStore.setUploadedByUsername(plantDto.getUploadedByUserName);

            repository.save(plantToUpdate);
        }
        //return saved.getId();
    }

//    @Override
//    public void updatePlant(Long id, Plant plant) {}
//    public void updatePlant(Long id, Plant newPlant) {
//        if(!plantRepository.existsById(id)) throw new RecordNotFoundException();
//        Plant plant = plantRepository.findById(id).get();
//
//        plantRepository.save(plant);
//    }

    @Override
    public Collection<Plant> findAllByNameContains(String name) {
        if (name.isEmpty()) {
            //return (List<Plant>) repository.findAll();
            throw new RecordNotFoundException();
        } else {
            return repository.findAllByNameContains(name);
        }
    }

    @Override
    public Collection<Plant> findAllByLatinNameContains(String latinName) {
        if (latinName.isEmpty()) {
            throw new RecordNotFoundException();
            //return (List<Plant>) repository.findAll();
        } else {
            return repository.findAllByLatinNameContains(latinName);
        }
    }
//    @Override
//    public List<Plant>getAllByUploadedByUsername(String uploadedByUserName) {
//        if (uploadedByUserName.isEmpty()) {
//            throw new RecordNotFoundException();
//            //return (List<Plant>) repository.findAll();
//        } else {
//            return repository.getAllByUploadedByUsername(uploadedByUserName);
//        }
//    }

    @Override
    public List<Plant> getPlantsByLatinNameContainsAndNameContains(String name, String latinName) {
        if (name.isEmpty() && latinName.isEmpty()) {
            throw new RecordNotFoundException();
            //return (List<Plant>) repository.findAll();
        } else {
            return repository.getPlantsByLatinNameContainsAndNameContains(name, latinName);
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



//    @Override
//    public boolean fileExistsById(long id) {
//        return repository.existsById(id);
//    }

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
    public Collection<Plant> findAllByDifficulty(Difficulty difficulty) {
        return repository.findAllByDifficulty(difficulty);
    }

    @Override
    public Collection<Plant> findAllByLight(Light light) {
        return repository.findAllByLight(light);
    }

    @Override
    public Collection<Plant> findAllByWatering(Watering watering) {
        return repository.findAllByWatering(watering);
    }

    @Override
    public Collection<Plant> findAllByFood(Food food) {
        return repository.findAllByFood(food);
    }

}
