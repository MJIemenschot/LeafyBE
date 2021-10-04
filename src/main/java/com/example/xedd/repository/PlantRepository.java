package com.example.xedd.repository;

import com.example.xedd.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlantRepository extends CrudRepository<Plant, Long> {
    Collection<Plant> findAllByNameContains(String name);
    Collection<Plant> findAllByWatering(Watering watering);
    Collection<Plant> findAllByDifficulty(Difficulty difficulty);
    Collection<Plant> findAllByFood(Food food);
    Collection<Plant> findAllByLight(Light light);
    Collection<Plant> findAllByLatinNameContains(String latinName);
    Collection<Plant>getAllByName(String name);
    List<Plant>getPlantsByLatinNameContainsAndNameContains(String latinName, String name);
    //List<Plant>getAllByUploadedByUsername(String uploadedByUserName);
    //
    //List<Plant>findPlantsByNameContainingOrderByNameDesc(String query);
    //Page<Plant> getBy(Pageable pageable);
    List<Plant>findAll();
    //List<Plant>getByNameIsStartingWith(String name);

   // boolean existsPlantByIdExists(long id);
}
