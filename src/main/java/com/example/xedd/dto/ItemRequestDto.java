package com.example.xedd.dto;

import com.example.xedd.model.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ItemRequestDto {

    private String name;
    private String description;
    private MultipartFile file;
    private Difficulty difficulty;
    private Light light;
    private Watering watering;
    private Food food;
    private String username;
//    private boolean difficult;
//    private boolean moderate;
//    private boolean easy;

}
