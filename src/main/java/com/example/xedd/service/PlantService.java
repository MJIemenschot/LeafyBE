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
import java.util.Map;

@Service
public interface PlantService {
    void init();
    List<Plant> getPlants();
    Page<Plant> findAllPlants(Pageable pageable);
    PlantResponseDto getPlantById(long id);
    boolean existsById(long id);
    void updatePlant(PlantRequestDto plantDto);
    void partialUpdatePlant(PlantRequestDto plantDto);
    public String uploadFile(MultipartFile file);
    public void uploadImage(PlantRequestDto plantDto);
    long addPlant(PlantRequestDto plantDto);
    void deletePlant(long id);
    Resource downloadFile(long id);
    List<Plant> findByName(String query);
    List<Plant> findByLatin(String query);
    //List<Plant>getAllByUploadedByUsername(String uploadedByUserName);
    List<Plant> findAllByWatering(Watering watering);
    List<Plant> findAllByDifficulty(Difficulty difficulty);
    List<Plant> findAllByFood(Food food);
    List<Plant> findAllByLight(Light light);



}
