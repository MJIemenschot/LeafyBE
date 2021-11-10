package com.example.xedd.dto;

import com.example.xedd.model.*;
import lombok.Data;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Clob;

@Data
public class PlantRequestDto {

    private long id;
    private String name;
    private String latinName;
    private String description;
    private String care;
    private String potting;
    public MultipartFile file;
    private Difficulty difficulty;
    private Light light;
    private Watering watering;
    private Food food;

    public PlantRequestDto(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


    public Plant toPlant() {
        var plant = new Plant();
        plant.setName(name);
        plant.setLatinName(latinName);
        plant.setDescription(description);
        plant.setCare(care);
        plant.setPotting(potting);
        //plant.setFile(file);
        plant.setDifficulty(difficulty);
        plant.setLight(light);
        plant.setWatering(watering);
        plant.setFood(food);
        return plant;

    }
}
