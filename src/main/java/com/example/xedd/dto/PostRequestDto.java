package com.example.xedd.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class PostRequestDto {
    private String name;
    private String description;
    private MultipartFile image;
}
