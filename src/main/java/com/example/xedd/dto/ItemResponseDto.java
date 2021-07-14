package com.example.xedd.dto;

import com.example.xedd.model.Item;
import com.example.xedd.service.ItemService;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import com.example.xedd.model.Item;
import javax.persistence.Column;
import java.util.Date;


public class ItemResponseDto {
    public String name;
    public String description;
    public String toPicture;
    public boolean isSeed;

//    public ItemResponseDto(String name, String description, String fileName, String toPicture) {
//        this.name = name;
//        this.description = description;
//
//        this.toPicture = toPicture;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//
//    public void setToPicture(String toPicture) {
//        this.toPicture = toPicture;
//    }
    //    public Item toItem(){
//        var item = new item();
//        item.setName(setName);
//        item.setDescription(description);
//        item.setToPicture(toPicture);
//        return Item;
//    }
}
