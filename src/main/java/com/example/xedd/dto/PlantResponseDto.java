package com.example.xedd.dto;

import com.example.xedd.model.Difficulty;
import com.example.xedd.model.Food;
import com.example.xedd.model.Light;
import com.example.xedd.model.Watering;
import lombok.Data;

import java.util.Date;

@Data
public class PlantResponseDto {
    private long id;
    private String name;
    private String latinName;
    private String description;
    private String fileName;
    private String mediaType;
    private String downloadUri;
    private Difficulty difficulty;
    private Light light;
    private Watering watering;
    private Food food;
    private String uploadedByUsername;
    private Date uploadedDate;
}
