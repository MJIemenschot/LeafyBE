package com.example.xedd.service;

import com.example.xedd.exception.RecordNotFoundException;
//import com.example.xedd.model.Item;
import com.example.xedd.model.Post;
import com.example.xedd.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService{

//    private String fileStoragePath ="\\Users\\mieme\\Desktop\\uploaded";
//    private String fileStorageLocation;
//    @Value("${uploadDir}")
//    private String uploadFolder;
//@Value("${app.upload.dir:${user.home}}")
////@Value("${uploads}")
//private String uploads;  // relative to root
//    private final Path upload = Paths.get(".\\uploads");

    @Autowired
    PostRepository postRepository;

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
    @Override
    public void deletePost(long id) {
        if (!postRepository.existsById(id)) throw new RecordNotFoundException();
        postRepository.deleteById(id);
    }
    @Override
    public void updatePost(long id, Post post) {
        if (!postRepository.existsById(id)) throw new RecordNotFoundException();
        Post existingPost = postRepository.findById(id).get();
        existingPost.setName(post.getName());
        existingPost.setDescription(post.getDescription());
        existingPost.setImage(post.getImage());

        postRepository.save(existingPost);

    }
    ////////////////////////////////////
//    @Override
//    public void uploadFile(MultipartFile image){
//        try {
//            byte[] data = image.getBytes();
//            Path path = Paths.get(fileStoragePath + image.getOriginalFilename());
//            Files.write(path, data);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public void uploadToLocal(MultipartFile file) {
//
//        try {
//            byte[] data = file.getBytes();
//            Path path = Paths.get(uploadFolderPath + file.getOriginalFilename());
//            Files.write(path, data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
/////
//@Override
//public void addPost(MultipartFile image, String name, String description){
//
//    }
/////    @Override
//    public UploadedFile uploadToDb(MultipartFile file) {
//
//        UploadedFile uploadedFile = new UploadedFile();
//        try {
//            uploadedFile.setFileData(file.getBytes());
//            uploadedFile.setFileType(file.getContentType());
//            uploadedFile.setFileName(file.getOriginalFilename());
//            UploadedFile uploadedFileToRet = fileUploadRepository.save(uploadedFile);
//            return uploadedFileToRet;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    @Override
//    public UploadedFile downloadFile(String fileId) {
//        UploadedFile uploadedFileToRet = fileUploadRepository.getOne(fileId);
//        return uploadedFileToRet;
//    }
//}


}