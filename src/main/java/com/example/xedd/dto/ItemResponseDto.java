package com.example.xedd.dto;

import com.example.xedd.model.Difficulty;
import com.example.xedd.model.Item;
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
    public String name;
    public String description;
    private String toPicture;
    private String location;
    private String fileName;
    private String mediaType;
    private String downloadUri;
//    private Difficulty difficulty;
//    private String username;
//    private boolean difficult;
//    private boolean moderate;
//    private boolean easy;
//    private Date uploadedDate;
}
