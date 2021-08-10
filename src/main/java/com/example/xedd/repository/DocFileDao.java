package com.example.xedd.repository;

import com.example.xedd.model.FileDocument;
import org.springframework.data.repository.CrudRepository;

public interface DocFileDao extends CrudRepository <FileDocument, Long> {
    FileDocument findByFileName(String fileName);
}
