package com.example.xedd.repository;


import com.example.xedd.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

 }


