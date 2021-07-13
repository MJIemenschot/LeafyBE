package com.example.xedd.dto;

import com.example.xedd.model.Item;
import com.example.xedd.service.ItemService;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.Date;


public class ItemResponseDto {
    public String name;
    public String description;
    public String fileName;
    public String mediaType;
    public String toPicture;

//    public Item toItem(){
//        var item = new item();
//        item.setName(setName);
//        item.setDescription(description);
//        item.setFileName(fileName);
//        item.setToPicture(toPicture);
//
//        return Item;
//    }
}
