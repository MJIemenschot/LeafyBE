package com.example.xedd.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;


import com.example.xedd.dto.FileUploadResponse;
import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.Post;
import com.example.xedd.repository.PostRepository;
import com.example.xedd.service.FileStorageService;
import com.example.xedd.service.PostService;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "posts")
public class PostController {

//    @Value("${uploadDir}")
//    private String uploadFolder;
@Value("${app.upload.dir:${user.home}}")
private String uploads;  // relative to root
    private final Path upload = Paths.get(".\\uploads");

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

//    private static Logger log = LoggerFactory.getLogger(PostController.class);
//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
//    private FileStorageService fileStorageService;


//    public PostController(FileStorageService fileStorageService) {
//        this.fileStorageService = fileStorageService;
//       }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/post/savePost")
    public @ResponseBody ResponseEntity<?> savePost(@RequestParam("name") String name,
                                                          @RequestParam("category") String category, @RequestParam("description") String description, Model model, HttpServletRequest request
            ,final @RequestParam("image") MultipartFile file) {
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String uploadDirectory = request.getServletContext().getRealPath(uploads);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
            }
            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();
            log.info("Name: " + names[0]+" "+filePath);
            log.info("description: " + descriptions[0]);
            log.info("category: " + category);
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            Post post = new Post();
            post.setName(names[0]);
            post.setImage(imageData);
            post.setCategory(category);
            post.setDescription(descriptions[0]);
            post.setCreateDate(createDate);
            postService.savePost(post);
            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Post Saved With File - " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/post/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Post> post)
            throws ServletException, IOException {
        log.info("Id: " + id);
        post = postService.getPostById(id);
        //response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(post.get().getImage());
        response.getOutputStream().close();
    }

//    @PostMapping("/addP")
//    public String savePost(@RequestParam("pname") String name,
//                           @RequestParam("description") String description,
//                           @RequestParam("category") String category);

//    @PostMapping("/uploadP/local")
//    public void uploadPost(@RequestParam("file")MultipartFile multipartFile){
//        upPostService.uploadPost(multipartFile);
//
//    }
//    @PostMapping("/uploadP/DB")
//    public void uploadPdb(@RequestParam("file")MultipartFile multipartFile){
//
//    }

//    @PostMapping("/addP")
//    public String addPost(@RequestParam("file") MultipartFile file,
//                                    @RequestParam("title") String title,
//                                   @RequestParam ("description")String description)
//{
//
//    String fileName = fileStorageService.storeFile(file);
//
//    ///http://localhost:8089/download/abc.jpg
//    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
//            .path("/download/")
//            .path(fileName)
//            .toUriString();
//
//    String contentType = file.getContentType();
//
//    FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
//
//    return response.getUrl();
//
//}

//@PostMapping("upload")
//FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file,
//                                    @RequestParam("title") String title,
//                                    @RequestParam ("description")String description)
//{
//
//    String fileName = fileStorageService.storeFile(file);
//
//    ///http://localhost:8089/download/abc.jpg
//    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
//            .path("/download/")
//            .path(fileName)
//            .toUriString();
//
//    String contentType = file.getContentType();
//
//    FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
//
//    return response;
//
//}

}
