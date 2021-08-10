package com.example.xedd.service;

import com.example.xedd.dto.MessageRequestDto;
import com.example.xedd.dto.PostRequestDto;
import com.example.xedd.dto.PostResponseDto;
import com.example.xedd.model.Post;
import com.example.xedd.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
//
public interface PostService {

    // void savePost(MultipartFile file, String name, String description);

        void savePost(Post post);
        List<Post> getPosts();
        Optional<Post> getPostById(Long id);



}
