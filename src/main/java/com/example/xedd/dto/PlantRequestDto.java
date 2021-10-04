package com.example.xedd.dto;

import com.example.xedd.model.Difficulty;
import com.example.xedd.model.Food;
import com.example.xedd.model.Light;
import com.example.xedd.model.Watering;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class PlantRequestDto {
    private long id;
    private String name;
    private String latinName;
    private String description;
    private MultipartFile file;
    private Difficulty difficulty;
    private Light light;
    private Watering watering;
    private Food food;
}
