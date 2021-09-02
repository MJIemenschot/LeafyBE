package com.example.xedd.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;


import com.example.xedd.model.Post;
import com.example.xedd.service.PostService;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "")
public class PostController {

    //    @Value("${app.upload.dir:${user.home}}")
//    private String temp;  // relative to root
//    private final Path upload = Paths.get(".\\fileupl");
    @Value("${app.upload.dir:${user.home}}")
    private String fileupl;  // relative to root
    // private String uploadDirectory;  // relative to root
    private final Path upload = Paths.get(".\\fileupl");
    //
    @Autowired
    PostService postService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value="/savepost")
    public @ResponseBody ResponseEntity<?> savePost(@RequestParam("name") String name,
                                                    //@RequestParam("difficulty") enum difficulty,
                                                    @RequestParam("description") String description,
                                                    Model model, HttpServletRequest request
            ,final @RequestParam("image") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();

//            log.info("Name: " + names[0]+" "+filePath);
//            log.info("description: " + descriptions[0]);
            //log.info("price: " + price);
//            try {

            // Save the file locally
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(toImage)));
//                stream.write(file.getBytes());
//                stream.close();
//            } catch (Exception e) {
//                log.info("in catch");
//                e.printStackTrace();
//            }
            byte[] imageData = file.getBytes();
            Post post = new Post();
//            String toImage = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/api/v1/download/")
//                    .path(file.getOriginalFilename())
//                    .toUriString();
//            post.setToImage(toImage);
            post.setFileType(file.getContentType());
            post.setName(names[0]);
            post.setImage(imageData);
            //post.setPrice(price);
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
//    @GetMapping("post/image/{id}")
//    public ResponseEntity<Resource>getImage(@PathVariable long id){
//        Post postToReturn = postService.getPostById(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(postToReturn.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION,"inline" + postToReturn.getName())
//                .body(new ByteArrayResource(postToReturn.getImage()));
//    }

    @GetMapping("post/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Post> post)
            throws ServletException, IOException {
        log.info("Id: " + id);
        post = postService.getPostById(id);
        //////////////

        /////////////////
        //response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getOutputStream().write(post.get().getImage());
        response.getOutputStream().close();
    }

    @GetMapping("posts")
    public ResponseEntity<Object> getPosts()  { return ResponseEntity.ok().body(postService.getPosts()); }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }
    @PutMapping(value = "/post/update/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable("id") long id, @RequestBody Post post) {
        postService.updatePost(id, post);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Object> Post(@PathVariable long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    //////////////

    @PostMapping("upload/local")
    public ResponseEntity<Object> addP(@RequestParam("image")MultipartFile image,
                                       @RequestParam("name") String name,
                                       @RequestParam("description") String description)
    {
        postService.uploadFile(image);
        Post post = new Post();
        post.setName(name);
        post.setDescription(description);

        postService.savePost(post);

        return ResponseEntity.noContent().build();

        //unzipFiles(multipartFile);
        //     fileUploadService.uploadToLocal(multipartFile);


    }
    //  @PostMapping("/upload/db")

//    public FileUploadResponse uploadDb(@RequestParam("file")MultipartFile multipartFile)
//    {
//        UploadedFile uploadedFile = fileUploadService.uploadToDb(multipartFile);
//        FileUploadResponse response = new FileUploadResponse();
//        if(uploadedFile!=null){
//            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/api/v1/download/")
//                    .path(uploadedFile.getFileId())
//                    .toUriString();
//            response.setDownloadUri(downloadUri);
//            response.setFileId(uploadedFile.getFileId());
//            response.setFileType(uploadedFile.getFileType());
//            response.setUploadStatus(true);
//            response.setMessage("File Uploaded Successfully!");
//            return response;
//
//        }
//        response.setMessage("Oops 1 something went wrong please re-upload.");
//        return response;
//    }
//    @GetMapping("/download/{id}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String id)
//    {
//        UploadedFile uploadedFileToRet =  fileUploadService.downloadFile(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(uploadedFileToRet.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename= "+uploadedFileToRet.getFileName())
//                .body(new ByteArrayResource(uploadedFileToRet.getFileData()));
//    }


    //////////////////////////////

//
//    @PostMapping("post/save")
//    public @ResponseBody ResponseEntity<?> savePost(@RequestParam("name") String name,
////                                                          @RequestParam("category") String category,
//                                                    @RequestParam("description") String description,
//                                                    Model model, HttpServletRequest request
//            ,final @RequestParam("image") MultipartFile file) {
//        try {
//            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
//            String uploadDirectory = request.getServletContext().getRealPath(temp);
//            log.info("uploadDirectory:: " + uploadDirectory);
//            String fileName = file.getOriginalFilename();
//            String filePath = Paths.get(uploadDirectory, fileName).toString();
//            log.info("FileName: " + file.getOriginalFilename());
//            if (fileName == null || fileName.contains("..")) {
//                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
//                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
//            }
//            String[] names = name.split(",");
//            String[] descriptions = description.split(",");
//            Date createDate = new Date();
//            log.info("Name: " + names[0]+" "+filePath);
//            log.info("description: " + descriptions[0]);
////            log.info("category: " + category);
//            try {
//                File dir = new File(uploadDirectory);
//                if (!dir.exists()) {
//                    log.info("Folder Created");
//                    dir.mkdirs();
//                }
//                // Save the file locally
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
//                stream.write(file.getBytes());
//                stream.close();
//            } catch (Exception e) {
//                log.info("in catch");
//                e.printStackTrace();
//            }
//            byte[] imageData = file.getBytes();
//            Post post = new Post();
//            post.setName(names[0]);
//            post.setImage(imageData);
////            post.setCategory(category);
//            post.setDescription(descriptions[0]);
//            post.setCreateDate(createDate);
//            postService.savePost(post);
//            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
//            return new ResponseEntity<>("Post Saved With File - " + fileName, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("Exception: " + e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


}