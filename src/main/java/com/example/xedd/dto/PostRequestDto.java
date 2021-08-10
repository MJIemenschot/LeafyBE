package com.example.xedd.dto;

import org.springframework.web.multipart.MultipartFile;

public class PostRequestDto {
    private String name;
    private String description;
    private MultipartFile file;
}
