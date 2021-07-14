package com.example.xedd.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    void uploadFile(MultipartFile toPicture);

    void deleteFile(String filename) throws IOException;
//    Resource downloadFile(long id);

}
