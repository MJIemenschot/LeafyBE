package com.example.xedd.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ITEMS")
public class Item {
    @Column
    @Id
    //@GeneratedValue
    private long id;
    
    private String name;

    private String description;

    private String toPicture;

    private boolean isSeed;

    private boolean isEnt;

    private boolean isPlant;

//    @Column(name = "uploaded_by_username")
//    private String uploadedByUsername;  of:
    //    @ManyToOne
//    User user;
//    private Date uploadedTimestamp;
//
//    LocalDateTime sentTime;

    //constructor empty

    public Item() {
    }
    //constructor


    public Item(long id, String name, String description, String toPicture, boolean isSeed, boolean isEnt, boolean isPlant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.toPicture = toPicture;
        this.isSeed = isSeed;
        this.isEnt = isEnt;
        this.isPlant = isPlant;

    }
    //getters and setters

    public long getId() { return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToPicture() { return toPicture; }

    public void setToPicture(String toPicture) { this.toPicture = toPicture; }

    public boolean isSeed() { return isSeed; }

    public void setSeed(boolean seed) { isSeed = seed; }

    public boolean isEnt() { return isEnt; }

    public void setEnt(boolean ent) {
        isEnt = ent;
    }

    public boolean isPlant() { return isPlant; }

    public void setPlant(boolean plant) { isPlant = plant; }
}
