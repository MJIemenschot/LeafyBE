package com.example.xedd.dto;

import com.example.xedd.model.*;
import lombok.Data;

import java.util.Date;

@Data
public class PlantResponseDto {
    private long id;
    private String name;
    private String latinName;
    private String description;
    private String care;
    private String potting;
    private String fileName;
    private String mediaType;
    private String downloadUri;
    private Difficulty difficulty;
    private Light light;
    private Watering watering;
    private Food food;
    private String uploadedByUsername;
    private Date uploadedDate;
    private String location;

    public static PlantResponseDto fromPlant(Plant plant) {
        if (plant == null) return null;

        var dto = new PlantResponseDto();
        dto.id = plant.getId();
        dto.name = plant.getName();
        dto.description = plant.getDescription();
        dto.care = plant.getCare();
        dto.potting =plant.getPotting();
        dto.fileName = plant.getFileName();
        dto.mediaType = plant.getMediaType();
        dto.downloadUri = plant.getDownloadUri();
        dto.difficulty = plant.getDifficulty();
        dto.light = plant.getLight();
        dto.watering = plant.getWatering();
        dto.food = plant.getFood();
        dto.uploadedByUsername = plant.getUploadedByUsername();
        dto.uploadedDate = plant.getUploadedDate();
        dto.location = plant.getLocation();
        return dto;
    }
}
