package com.example.xedd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.example.xedd.dto.FileUploadResponse;
import com.example.xedd.model.FileDocument;
import com.example.xedd.repository.DocFileDao;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin(value = {"*"})
@RequestMapping()
public class UploadDownloadWithDatabaseController {



    private DocFileDao docFileDao;

    public UploadDownloadWithDatabaseController(DocFileDao docFileDao){
        this.docFileDao = docFileDao;
    }
    @PostMapping("uploadDB")
    FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        String name = StringUtils.cleanPath(file.getOriginalFilename());
        FileDocument fileDocument= new FileDocument();
        fileDocument.setFileName(name);
        fileDocument.setDocFile(file.getBytes());

        docFileDao.save(fileDocument);

        ///http://localhost:8080/download/abc.jpg
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFromDB/")
                .path(name)
                .toUriString();

        String contentType = file.getContentType();

        FileUploadResponse response = new FileUploadResponse(name, contentType, url);

        return response;

    }
    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        FileDocument doc = docFileDao.findByFileName(fileName);

        docFileDao.findByFileName(fileName);

//        MediaType contentType = MediaType.APPLICATION_PDF;

          String  mimeType = request.getServletContext().getMimeType(doc.getFileName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.getFilename())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + doc.getFileName())
                .body(doc.getDocFile());
    }
}
