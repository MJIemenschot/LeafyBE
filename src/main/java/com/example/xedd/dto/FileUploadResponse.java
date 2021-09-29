package com.example.xedd.dto;

import lombok.Data;

@Data
public class FileUploadResponse {

    //private String title;

    public String fileName;

    public String contentType;

    public String url;

    public FileUploadResponse(
            //String title,
                              String fileName,
                              String contentType,
                              String url) {
        this.fileName = fileName;
        //this.title = title;
        this.contentType = contentType;
        this.url = url;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
