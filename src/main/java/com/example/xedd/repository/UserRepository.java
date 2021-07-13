package com.example.xedd.repository;

import com.example.xedd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, String> {
}
