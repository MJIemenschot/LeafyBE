package com.example.xedd.dto;

import com.example.xedd.model.*;
import com.example.xedd.service.ItemService;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import com.example.xedd.model.Item;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class ItemResponseDto {
    private Long id;
    public String name;
    public String description;
    private String toPicture;
    private MultipartFile file;
    //private String location;
    private String fileName;
    private String mediaType;
    //private String downloadUri;
    private Difficulty difficulty;
    private Light light;
    private Watering watering;
    private Food food;
    private String username;
    private Date uploadedDate;
}
