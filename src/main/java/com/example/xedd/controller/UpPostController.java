//package com.example.xedd.controller;
//
//import com.example.xedd.model.Item;
//import com.example.xedd.model.UpPost;
//import com.example.xedd.service.UpPostService;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.nio.file.Paths;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
////import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.persistence.Entity;
//
//
//@RestController
//@CrossOrigin("*")
//@RequestMapping(value = "upposts")
//public class UpPostController {
//
//    private static Logger log = LoggerFactory.getLogger(UpPostController.class);
//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
//
//    @Autowired
//    UpPostService upPostService;
//    private List<UpPost> upPosts = new ArrayList<>();
//
//    @PostMapping("/savePost")
//    public @ResponseBody
//    ResponseEntity<?> saveUpPost( UpPost upPost,
//                                     @RequestParam("name") final String name, @RequestParam("description") final String description,
//                                     final @RequestParam("file") MultipartFile file) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//
//            if (upPost == null) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            String[] descr = description.split(",");
//            String imageName = file.getOriginalFilename();
//            String imagePath = Paths.get(uploadDirectory, imageName).toString();
//            String fileType = file.getContentType();
////            long size = file.getSize();
////            String fileSize = String.valueOf(size);
//            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//
//            log.info("Name: " + name);
//            log.info("Description: " + descr[0]);
//            log.info("ImageName: " + file.getOriginalFilename());
//            log.info("FileType: " + file.getContentType());
////            log.info("FileSize: " + file.getSize());
//
//            // Save the file locally
//            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(imagePath)));
//            stream.write(file.getBytes());
//            stream.close();
//
//            upPost.setName(name);
//            upPost.setDescription(descr[0]);
//            upPost.setImageName(imageName);
//            upPost.setImagePath(imagePath);
//
//            //upPost.setCreatedDate(currentTimestamp);
//
//            boolean status = upPostService.saveUpPost(upPost);
//            if (status) {
//                log.info("Post Created");
//                headers.add("Post Saved With Image - ", imageName);
//                return new ResponseEntity<>("Post Saved With File - " + imageName, headers, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("Exception: " + e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
////
//////    @PostMapping("/uploadP/local")
//////    public void uploadPLocal(@RequestParam("file") MultipartFile multipartFile){
//////        upPostService.uploadPost(multipartFile);
//////
//////    }
//////    @PostMapping("/uploadP/DB")
//////    public void uploadPDb(@RequestParam("file")MultipartFile multipartFile){
//////        upPostService.uploadToDb(multipartFile);
////
////
////   // }
//////    @GetMapping("/downloadUp/{id}")
//////            public UpPost downloadFile(@PathVariable long id){
//////        return upPostService.downloadPost(id);
//////
//////
//////    }
////
////
//}
