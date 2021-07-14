package com.example.xedd.dto;

import lombok.Data;
import com.example.xedd.model.Item;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ItemRequestDto {
    public long id;//?
    public String name;
    public String description;
    public MultipartFile toPicture;

    public static ItemRequestDto fromItem(Item item) {
        var dto = new ItemRequestDto();
        dto.id = item.getId();
        dto.name = item.getName();
        dto.description = item.getDescription();
       //dto.toPicture = toPicture;
        return dto;
    };


}
