package com.example.xedd.service;

import com.example.xedd.dto.PlantResponseDto;
import com.example.xedd.model.Plant;
import com.example.xedd.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PlantServiceImplTest {

    @Mock
    PlantRepository plantRepository;

    @InjectMocks
    private PlantServiceImpl plantService;

    @Captor
    ArgumentCaptor<Plant> plantArgumentCaptor;

    @Test
    public void getPlantsTest(){
        when(plantRepository.findAll()).thenReturn(List.of(new Plant(), new Plant(), new Plant()));
        List<Plant> plantList = plantService.getPlants();
        assertEquals(3, plantList.size());
    }

        @Test
    public void getPlantByIdTest() {
        Plant plant = new Plant();
        plant.setId(1L);
        Long id = plant.getId();
//        when(plantRepository.findById(id)).thenReturn(Optional.of(plant));
//        Optional<Plant> plantOptional = plantService.getPlantById(id);
//        assertTrue(plantOptional.isPresent());
//        assertEquals(id, plantOptional.get().getId());
    }

    @Test
    void addPlantTest() {
        Plant testPlant = new Plant();
        testPlant.setId(1L);
        testPlant.setName("testplant");
        testPlant.setDescription("Holy moly, etc etc etc etc etc");
//        testPlant.setDifficulty("HARD");
//        testPlant.setWatering("WEEK");
//        testPlant.setLight("SUNNY");
//        testPlant.setFood("MONTH");
//        when(plantRepository.save(testPlant)).thenReturn(testPlant);
//        plantService.addPlant(testPlant);
//        verify(plantRepository).save(plantArgumentCaptor.capture());
//        Plant newPlant = plantArgumentCaptor.getValue();
//        assertThat(testPlant.getId().equals(newPlant.getId()));

    }

//        @Test
//    public void updatePlantTest() {
//        Plant testPlant = newPlant();
//        testPlant.setName("testplant2");
//        testPlant.setDescription("Hali Halo etc etc etc etc");
//        testPlant.setDifficulty("HARD");
//        testPlant.setWatering("WEEK");
//       testPlant.setLight("SUNNY");
//        testPlant.setFood("MONTH");
//        Plant update = new Plant();
//
//        when(plantRepository.existsById(update.getId())).thenReturn(true);
//        when(plantRepository.findById(update.getId())).thenReturn(Optional.of(testPlant));
//        plantServiceImpl.updatePlant(update.getId(), update);
//        verify(plantRepository).save(plantCaptor.capture());
//       Plant savedPlant =plantCaptor.getValue();
//
//    }

    @Test
    public void deletePlantTest() {
//        Plant plant = new Plant();
//        plant.setId(1L);
//        plantService.deletePlant(plant.getId());
//        verify(plantRepository).deleteById(plant.getId());
    }



}




