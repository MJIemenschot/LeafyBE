package com.example.xedd.repository;

import com.example.xedd.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long > {
    Collection<Item> findAllByName(String name);
    Collection<Item> getItemById(Long id);
    Collection<Item> findAllByDescription(String description);
    Collection<Item> findAllByNameAndDescription(String name, String description);


}
