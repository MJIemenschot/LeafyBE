package com.example.xedd.service;


import com.example.xedd.exception.FileStorageException;
import com.example.xedd.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService{

//   @Value("${app.upload.dir:${user.home}}")
//    private final Path toPictureStorageLocation = Paths.get("uploads");
//    @Value("${app.upload.dir:${user.home}}")
//    private String uploadDirectory;  // relative to root
//    private final String toPictureStorageLocation = "C:\\Users\\mieme\\IdeaProjects\\Xedd\\src\\uploads";

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDirectory;  // relative to root
    private final Path uploads = Paths.get(".\\uploads");

    @Override
    public void uploadFile(MultipartFile toPicture) {
        try {
            Path copyLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(toPicture.getOriginalFilename()));
            Files.copy(toPicture.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            Item item = new Item();
            item.setDescription(toPicture.getName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + toPicture.getOriginalFilename() + ". Please try again.");
        }
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        Path deleteLocation = Paths.get(uploads + File.separator + StringUtils.cleanPath(filename));
        Files.delete(deleteLocation);
    }
}

