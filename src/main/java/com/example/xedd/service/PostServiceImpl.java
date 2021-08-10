package com.example.xedd.service;

import com.example.xedd.dto.PostRequestDto;
import com.example.xedd.dto.PostResponseDto;
import com.example.xedd.exception.RecordNotFoundException;
//import com.example.xedd.model.Item;
import com.example.xedd.model.Post;
import com.example.xedd.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService{

    private Path fileStoragePath;
    private String fileStorageLocation;

    @Autowired
    PostRepository postRepository;

    public PostServiceImpl(@Value("${file.storage.location:temp}") String fileStorageLocation) {

        this.fileStorageLocation = fileStorageLocation;
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    @Override
    public void savePost(Post post) { postRepository.save(post); }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
//
//

//    private Path fileStoragePath;
//    private String fileStorageLocation;
//    private MultipartFile file;
//
//    //    @Value("${app.upload.dir:${user.home}}")
////    private String uploadDirectory;  // relative to root
////    private final Path uploads = Paths.get(".\\uploads");
//
//    public PostServiceImpl(@Value("${file.storage.location:loadImg}") String fileStorageLocation) {
//
//        this.fileStorageLocation = fileStorageLocation;
//        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
//
//        try {
//            Files.createDirectories(fileStoragePath);
//        } catch (IOException e) {
//            throw new RuntimeException("Issue in creating file directory");
//        }
//    }
//
//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
//
//    public Resource downloadPost(String fileName) {
//
//        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
//
//        Resource resource;
//        try {
//            resource = new UrlResource(path.toUri());
//
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Issue in reading the file", e);
//        }
//
//        if(resource.exists() && resource.isReadable()){
//            return resource;
//        }else{
//            throw new RuntimeException("the file doesn't exist or not readable");
//        }
//    }

//    @Override
//    public boolean savePost(Post post) throws IOException {
//        try {
//            if (post != null) {
//                postRepository.save(post);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//
//    }


//    public void savePost(MultipartFile file, String name, String description){
//        Post p = new Post();
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);
//        try {
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            p.setName(name);
//            p.setDescription(description);
//        } catch (IOException e) {
//            throw new RuntimeException("Issue in storing the file", e);
//        }
//
//        if(fileName.contains("..")){
//            System.out.println("not a valid file");
//        }
//   }
//    public String uploadPost(PostRequestDto postRequestDto) {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);
//
//        try {
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new RuntimeException("Issue in storing the file", e);
//        }
//        return fileName;
//    }


}
