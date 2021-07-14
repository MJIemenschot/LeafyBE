package com.example.xedd.dto;

import com.example.xedd.model.Item;
import com.example.xedd.service.ItemService;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import com.example.xedd.model.Item;
import javax.persistence.Column;
import java.util.Date;

@Data
public class ItemResponseDto {
    public String name;
    public String description;
    public String toPicture;
    public boolean isSeed;


}
