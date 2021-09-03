package com.example.xedd.dto;

import com.example.xedd.model.Difficulty;
import lombok.Data;
import com.example.xedd.model.Item;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ItemRequestDto {

    private String name;
    private String description;
    private MultipartFile file;
    private Difficulty difficulty;
    private String username;
//    private boolean difficult;
//    private boolean moderate;
//    private boolean easy;

}
