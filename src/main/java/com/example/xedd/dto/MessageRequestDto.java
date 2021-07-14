package com.example.xedd.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageRequestDto {
    private String title;
    private String description;
    private MultipartFile file;
}
