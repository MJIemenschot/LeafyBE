package com.example.xedd.repository;

import com.example.xedd.model.Difficulty;
import com.example.xedd.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long > {
    Collection<Item> findAllByName(String name);
    Collection<Item> findByDifficulty(Difficulty difficulty);
//    Collection<Item> getItemById(Long id);
//    Collection<Item> findAllByDescription(String description);
//    Collection<Item> findAllByNameAndDescription(String name, String description);



}
