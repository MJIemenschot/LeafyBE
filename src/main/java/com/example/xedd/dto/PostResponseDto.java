package com.example.xedd.dto;

import lombok.Data;

import java.util.Date;
@Data
public class PostResponseDto {
    private long id;
    private String fileType;
    private String name;
    private String description;
    private byte[] image;
    private String byUser;
    private boolean uploadStatus;
    private String message;
    private Date createDate;
    private String downloadUri;
}
