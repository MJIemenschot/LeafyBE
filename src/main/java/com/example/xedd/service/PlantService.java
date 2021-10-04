package com.example.xedd.service;

import com.example.xedd.dto.PlantRequestDto;
import com.example.xedd.dto.PlantResponseDto;
import com.example.xedd.model.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface PlantService {
    void init();
    List<Plant> findAll();
    //Page<Plant> getAll(Pageable pageable);
    PlantResponseDto getFileById(long id);
//    boolean fileExistsById(long id);
   // boolean existsPlantByIdExists(long id);
    void updatePlant(PlantRequestDto plantDto);
    long uploadFile(PlantRequestDto plantDto);
    void deleteFile(long id);
    Resource downloadFile(long id);
    Collection<Plant> findAllByNameContains(String name);
    Collection<Plant> findAllByLatinNameContains(String latinName);
    //List<Plant>getAllByUploadedByUsername(String uploadedByUserName);
    List<Plant>getPlantsByLatinNameContainsAndNameContains(String name, String latinName);
    Collection<Plant> findAllByWatering(Watering watering);
    Collection<Plant> findAllByDifficulty(Difficulty difficulty);
    Collection<Plant> findAllByFood(Food food);
    Collection<Plant> findAllByLight(Light light);


}
