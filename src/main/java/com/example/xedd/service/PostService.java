package com.example.xedd.service;

import com.example.xedd.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
//
public interface PostService {

     //void createPost(PostRequestDto postRequestDto);
     void uploadFile(MultipartFile image);
     //void addPost(MultipartFile image, String name, String description);
     void savePost(Post post);
     List<Post> getPosts();
     Optional<Post> getPostById(Long id);
     // void partialUpdatePost(long id, Map<String, String> fields);
     void updatePost(long id, Post post);
     void deletePost(long id);



}