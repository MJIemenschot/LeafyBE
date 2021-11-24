package com.example.xedd.repository;

import com.example.xedd.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlantRepository extends CrudRepository<Plant, Long> {
    //List<Plant> findAllByNameContainsIgnoreCase(String query);
    //List<Plant> findAllByLatinNameContainsIgnoreCase(String query);
    List<Plant> findAllByWatering(Watering watering);
    List<Plant> findAllByDifficulty(Difficulty difficulty);
    List<Plant> findAllByFood(Food food);
    List<Plant> findAllByLight(Light light);
    //Collection<Plant>getAllByNameIgnoreCase(String name);
    List<Plant>findPlantsByNameContainingIgnoreCase(String query);
    List<Plant>findPlantsByLatinNameContainingIgnoreCase(String query);
    List<Plant>findAll();
    //List<Plant>getPlantByUploadedByUsername();
    Page<Plant>findAll(Pageable pageable);
    boolean existsPlantByName(String name);

    boolean existsById(long id);
}

