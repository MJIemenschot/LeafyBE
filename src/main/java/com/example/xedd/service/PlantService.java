package com.example.xedd.service;

import com.example.xedd.dto.PlantRequestDto;
import com.example.xedd.dto.PlantResponseDto;
import com.example.xedd.model.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Service
public interface PlantService {
    void init();
    List<Plant> getPlants();
    Page<Plant> findAllPlants(Pageable pageable);
    PlantResponseDto getPlantById(long id);
    boolean existsById(long id);
    void updatePlant(PlantRequestDto plantDto);
    void PartialUpdatePlant(PlantRequestDto plantDto);
//    void updatPlant(long id,Plant plant);
    //void editPlant(PlantRequestDto plantDto);
   // public Plant editoPlant(long id, Plant plant);
    public String uploadFile(MultipartFile file);
    public void uploadImage(PlantRequestDto plantDto);
    long addPlant(PlantRequestDto plantDto);
    void deletePlant(long id);
    Resource downloadFile(long id);
//    Collection<Plant> findAllByName(String name);
//    Collection<Plant> findAllByLatinName(String latinName);
//    Collection<Plant> getByName(String name);
//    Collection<Plant> findAllByName(String query);
//    Collection<Plant> findAllByLatinName(String query);
    List<Plant> findByName(String query);
    List<Plant> findByLatin(String query);
    //List<Plant>getAllByUploadedByUsername(String uploadedByUserName);
    Collection<Plant> findAllByWatering(Watering watering);
    Collection<Plant> findAllByDifficulty(Difficulty difficulty);
    Collection<Plant> findAllByFood(Food food);
    Collection<Plant> findAllByLight(Light light);


}
