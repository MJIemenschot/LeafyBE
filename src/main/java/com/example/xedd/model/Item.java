package com.example.xedd.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "items")
public class Item {
    @Column
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String description;
    private String toPicture;
    private String fileName;
    private String mediaType;
    private String location;

    //@Enumerated(value = EnumType.STRING)
    //private Difficulty difficulty;

    private Date uploadedDate;



//    @Column(name = "uploaded_by_username")
//    @ManyToOne
//    @JoinColumn(name="user_id")
    //private String user;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;



//    public Set<Category> getCategories(){
//        return categories;
//    }
//
//    private Date uploadedTimestamp;
//
//    LocalDateTime sentTime;

    //constructor empty

    public Item() {
    }
    //constructor


//    public Item(Difficulty difficulty) {
//        this.difficulty = difficulty;
//    }

    public Item(long id, String name, String description, String toPicture,String fileName, String mediaType, String location,Date uploadedDate, String user, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.toPicture = toPicture;
        this.uploadedDate = uploadedDate;
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.location = location;
        //this.user = user;
        this.category = category;
    }

    public long getId() {
        return id;
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

    public String getToPicture() {
        return toPicture;
    }

    public void setToPicture(String toPicture) {
        this.toPicture = toPicture;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedTimestamp) {
        this.uploadedDate = uploadedTimestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

//    public Difficulty getDifficulty() {
//        return difficulty;
//    }
//
//    public void setDifficulty(Difficulty difficulty) {
//        this.difficulty = difficulty;
//    }
}
