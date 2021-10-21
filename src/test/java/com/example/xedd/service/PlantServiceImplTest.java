package com.example.xedd.service;

import com.example.xedd.dto.PlantRequestDto;
import com.example.xedd.model.*;
import com.example.xedd.repository.PlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PlantServiceImplTest {

    @Mock
    PlantRepository repository;

    @Mock
    PlantRequestDto plant;

    @InjectMocks
    private PlantServiceImpl plantServiceImpl;

    @Captor
    ArgumentCaptor<Plant> plantArgumentCaptor;


@BeforeEach
public void setup() {

    Plant plantOne = new Plant();
    plantOne.setId((long) 1);
    plantOne.setName("Cactus");
    plantOne.setLatinName("Cacto");
    plantOne.setDescription("All cactusses are cool ");
    plantOne.setCare("Cactusses need sunlight ");
    plantOne.setDifficulty(Difficulty.EASY);
    plantOne.setLight(Light.DIRECTSUN);
    plantOne.setWatering(Watering.MONTH);
    plantOne.setFood(Food.NEVER_SPECIAL);


    Plant plantTwo = new Plant();
    plantTwo.setId((long) 2);
    plantTwo.setName("Pauwenplant");
    plantTwo.setLatinName("Calathea");
    plantTwo.setDescription("All calatheas are cool ");
    plantTwo.setCare("Calatheas need care ");
    plantTwo.setDifficulty(Difficulty.HARD);
    plantTwo.setLight(Light.SUNNY);
    plantTwo.setWatering(Watering.TWODAYS);
    plantTwo.setFood(Food.MONTH);

    List <Plant> plantList = new ArrayList<>();
    plantList.add(plantOne);
    plantList.add(plantTwo);

    MockitoAnnotations.openMocks(this);

}


    @Test
    public void getPlantsTest(){
        when(repository.findAll()).thenReturn(List.of(new Plant(), new Plant(), new Plant()));
        List<Plant> plantList = plantServiceImpl.getPlants();
        assertEquals(3, plantList.size());
    }
    @Test
    public void findByNameTest(){
        Plant plant = new Plant();
        plant.setId(1L);
        plant.setName("Cactus");
        String query = plant.getName();
        when(repository.findPlantsByNameContainingIgnoreCase("Cactus")).thenReturn(List.of(new Plant ()));

        assertEquals(1, plantServiceImpl.findByName(query).size());
    }
    @Test
    public void findByLatinNameTest(){
        Plant plant = new Plant();
        plant.setId(1L);
        plant.setName("Calathea");
        String query = plant.getName();
        when(repository.findPlantsByLatinNameContainingIgnoreCase("Calathea")).thenReturn(List.of(new Plant ()));

        assertEquals(1, plantServiceImpl.findByLatin(query).size());
    }


    @Test
    public void getPlantByIdTest() {
        Plant plant = new Plant();
        plant.setId(1L);
        Long id = plant.getId();
        when(repository.findById(id)).thenReturn(Optional.of(plant));
        Optional<Plant> plantOptional = repository.findById(id);
        assertTrue(plantOptional.isPresent());
        assertEquals(1L, plantOptional.get().getId());
    }


    @Test
    public void deletePlantTest() {
        Plant plant = new Plant();
        plant.setId(2L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(plant));
        plantServiceImpl.deletePlant(2L);
        verify(repository, times(1)).deleteById(2L);
    }
    @Test
    public void deletePlant_thenVerifiedNotPresent(){
        //No plant is initialed, therefore Not present.
        plantServiceImpl.deletePlant(1L);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void findAllByLightTest(){
        Plant plant = new Plant();
        plant.setLight(Light.valueOf("SUNNY"));
        Light light = plant.getLight();
        when(repository.findAllByLight(light)).thenReturn(List.of(plant));
        List<Plant> plantList = repository.findAllByLight(light);
        assertTrue(plantList.contains(plant));
    }
    @Test
    public void findAllByDifficultyTest(){
        Plant plant = new Plant();
        plant.setDifficulty(Difficulty.valueOf("EASY"));
        Difficulty difficulty = plant.getDifficulty();
        when(repository.findAllByDifficulty(difficulty)).thenReturn(List.of(plant));
        List<Plant> plantList = repository.findAllByDifficulty(difficulty);
        assertTrue(plantList.contains(plant));
    }
    @Test
    public void findAllByWateringTest(){
        Plant plant = new Plant();
        plant.setWatering(Watering.valueOf("WEEK"));
        Watering watering = plant.getWatering();
        when(repository.findAllByWatering(watering)).thenReturn(List.of(plant));
        List<Plant> plantList = repository.findAllByWatering(watering);
        assertTrue(plantList.contains(plant));

    }
    @Test
    public void findAllByFoodTest(){
        Plant plant = new Plant();
        plant.setFood(Food.valueOf("MONTH"));
        Food food= plant.getFood();
        when(repository.findAllByFood(food)).thenReturn(List.of(plant));
        List<Plant> plantList = repository.findAllByFood(food);
        assertTrue(plantList.contains(plant));

    }


}




