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
    Collection<Plant> findAllByNameContainsIgnoreCase(String query);
    Collection<Plant> findAllByLatinNameContainsIgnoreCase(String query);
    Collection<Plant> findAllByWatering(Watering watering);
    Collection<Plant> findAllByDifficulty(Difficulty difficulty);
    Collection<Plant> findAllByFood(Food food);
    Collection<Plant> findAllByLight(Light light);

    Collection<Plant>getAllByNameIgnoreCase(String name);
    List<Plant>getPlantsByLatinNameContainsIgnoreCaseAndNameContainsIgnoreCase(String latinName, String name);
    //List<Plant>getAllByUploadedByUsername(String uploadedByUserName);
    //Page<Plant>getAll(Pageable pageable);

    //List<Plant>findPlantsByNameContainingOrderByNameDesc(String query);
    List<Plant>findAll();
    Page<Plant>findAll(Pageable pageable);

    boolean existsById(long id);
}
//@Repository
//public interface PlantRepository extends JpaRepository<Plant, Long> {
//    Collection<Plant> findAllByNameContainsIgnoreCase(String name);
//    Collection<Plant> findAllByLatinNameContainsIgnoreCase(String latinName);
//    Collection<Plant> findAllByWatering(Watering watering);
//    Collection<Plant> findAllByDifficulty(Difficulty difficulty);
//    Collection<Plant> findAllByFood(Food food);
//    Collection<Plant> findAllByLight(Light light);
//
//    Collection<Plant>getAllByNameIgnoreCase(String name);
//    List<Plant>getPlantsByLatinNameContainsIgnoreCaseAndNameContainsIgnoreCase(String latinName, String name);
//    //List<Plant>getAllByUploadedByUsername(String uploadedByUserName);
//    //Page<Plant>getAll(Pageable pageable);
//
//    //List<Plant>findPlantsByNameContainingOrderByNameDesc(String query);
//    List<Plant>findAll();
//    Page<Plant>findAll(Pageable pageable);
//
//    boolean existsById(long id);
//}
