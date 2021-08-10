//package com.example.xedd.service;
//
//import com.example.xedd.model.Message;
//import com.example.xedd.model.UpPost;
//import com.example.xedd.repository.UpPostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//
//
//@Service
//public class UpPostServiceImpl implements UpPostService {
//    @Autowired
//    UpPostRepository upPostRepository;
//
//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
//
//    @Override
//    public boolean saveUpPost(UpPost upPost) throws IOException {
//        try {
//            if (upPost != null) {
//                upPostRepository.save(upPost);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//
//    }
//
//    @Override
//    public List<UpPost> getAllUpPosts() {
//        return upPostRepository.findAll();
//    }
//
////    @Override
////    public boolean deleteFile(Long id, String file) {
////        boolean status = false;
////        try {
////            if (id != 0 && file != null) {
////                upPostRepository.deleteUpPostWithFile(id, file);
////                System.out.println(this.getClass().getSimpleName() + ":deleting uppost... " + id);
////                String path = uploadDirectory + "/" + file;
////                System.out.println("Path=" + path);
////                File fileToDelete = new File(path);
////                status = fileToDelete.delete();
////                System.out.println(this.getClass().getSimpleName() + ":deleting file... " + file);
////                System.out.println("Success: " + status + " fileToDelete: " + fileToDelete);
////                return status;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            return status;
////        }
////        return status;
////    }
//
////    @Autowired
////    private UpPostRepository upPostRepository;
//
////    private String uploadFolderPath = "\\Users\\mieme\\Documents\\uploaded\\";
////
////    @Override
////    public void uploadPost(MultipartFile file) {
////        try {
////            byte[] data = file.getBytes();
////            Path path = Paths.get(uploadFolderPath + file.getOriginalFilename());
////            Files.write(path, data);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////    }
////
////    public void uploadToDb(MultipartFile file) {
////        try {
////            UpPost upPost = new UpPost();
////            upPost.setFileData(file.getBytes());
////            upPost.setFileType(file.getContentType());
////            upPost.setFileName(file.getOriginalFilename());
////
////            UpPost saved = upPostRepository.save(upPost);
////
////
////        }catch (IOException e){
////            e.printStackTrace();
////        }
////    }
////    public UpPost downloadPost(long id){
////        UpPost stored = upPostRepository.getById(id);
////        return stored;
////    }
//}
