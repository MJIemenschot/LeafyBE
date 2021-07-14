package com.example.xedd.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageResponseDto {
    private String title;
    private String description;
    private String fileName;
    private String mediaType;
    private String downloadUri;
}
