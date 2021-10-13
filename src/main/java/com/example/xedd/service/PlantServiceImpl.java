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

    //500 vindt alleen latinName
//    @Override
//    public List<Plant> findByName(String query) {
//        List<Plant> found = repository.findPlantsByNameContainingIgnoreCase(query);
//        if (found.isEmpty()) {
//
//            return (List<Plant>) repository.findPlantsByLatinNameContainingIgnoreCase(query);
//
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }




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
    @Override
    public Page<Plant> findAllPlants(Pageable pageable){
        return repository.findAll(pageable);
    }
    //@Override
//    public Collection<Plant> findAllByName(String query){ return repository.findAllByNameContainsIgnoreCase(query); }
//
//    @Override
//    public Collection<Plant> findAllByLatinName(String query){ return repository.findAllByLatinNameContainsIgnoreCase(query); }

//    @Override
//    public Collection<Plant> findPlants(String name, String latinName) {
//        if (name.isEmpty()) && (latinName.isEmpty()) throw new RecordNotFoundException();
//        for (String name : String LatinName){
//            //return (List<Plant>) repository.findAll();
//            case "LatinName":
//                repository.findAllByLatinNameContainsIgnoreCase(latinName);
//                break;
//            case : "name":
//           repository.findAllByNameContainsIgnoreCase(name);
 //       }
  //  }
//werkt
//    @Override
//    public Collection<Plant> findAllByName(String name) {
//        if (name.isEmpty()) {
//            //return (List<Plant>) repository.findAll();
//            //return (Collection<Plant>) repository.findAllByLatinNameContainsIgnoreCase(query);
//            throw new RecordNotFoundException();
//        } else {
//            return repository.findAllByNameContainsIgnoreCase(name);
//        }
//    }
//
//    @Override
//    public Collection<Plant> findAllByLatinName(String latinName) {
//        if (latinName.isEmpty()) {
//            throw new RecordNotFoundException();
//            //return (List<Plant>) repository.findAll();
//        } else {
//            return repository.findAllByLatinNameContainsIgnoreCase(latinName);
//        }
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
    }
    public void PartialUpdatePlant(PlantRequestDto plantDto) {
        Optional<Plant> stored = repository.findById(plantDto.getId());
        if(stored.isPresent()) {
            var nPlant = stored.get();
            nPlant.setName(plantDto.getName());
            repository.save(nPlant);
        }
    }

//    @Override
//    public Plant editoPlant(long id, Plant plant) {return repository.save(plant);}

    //zonder nieuwe file
//    @Override
//    public void editPlant(Plant plant) {
//        if(!repository.existsById(id)) throw new RecordNotFoundException();
//        Plant editedPlant = repository.findById(id).get();
//        //editedPlant.setFileName(originalFilename);
//        //editedPlant.setLocation(copyLocation.toString());
//        editedPlant.setName(plant.getName());
//        editedPlant.setLatinName(plant.getLatinName());
//        editedPlant.setDescription(plant.getDescription());
//        editedPlant.setDifficulty(plant.getDifficulty());
//        editedPlant.setWatering(plant.getWatering());
//        editedPlant.setFood(plant.getFood());
//        editedPlant.setLight(plant.getLight());
//
//        repository.save(editedPlant);
//
//    }
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
        var optionalPlant = repository.findById(plantDto.getId());
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


//    @Override
//    public void updatPlant(long id, Plant newPlant) {
//        if(!repository.existsById(id)) throw new RecordNotFoundException();
//        Plant plant = repository.findById(id).get();
//        plant.setName(newPlant.getName());
//        plant.setLatinName(newPlant.getLatinName());
//        plant.setDescription(newPlant.getDescription());
//        plant.setDifficulty(newPlant.getDifficulty());
//        plant.setWatering(newPlant.getWatering());
//        plant.setLight(newPlant.getLight());
//        plant.setFood(newPlant.getFood());
//        String fileName = newPlant.getFileName();
//        if(!(fileName==null)){
//            plant.setLocation(newPlant.getFileName());}
//
//        repository.save(plant);
//    }

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
    public boolean existsById(long id) {
        return repository.existsById(id);
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
