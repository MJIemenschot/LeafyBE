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
    private String username;
//    private boolean difficult;
//    private boolean moderate;
//    private boolean easy;

//    @Enumerated(value = EnumType.STRING)
//    private Difficulty difficulty;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "uploaded_date")
//    private Date uploadedDate;

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

    public Item(long id, String name, String description, String toPicture,String fileName, String mediaType, String location,Date uploadedDate, String username, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.toPicture = toPicture;
        //this.uploadedDate = uploadedDate;
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.location = location;
        this.username = username;
        this.category = category;
//        this.difficult = difficult;
//        this.moderate = moderate;
//        this.easy = easy;
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

//    public Date getUploadedDate() {
//        return uploadedDate;
//    }
//
//    public void setUploadedDate(Date uploadedTimestamp) {
//        this.uploadedDate = uploadedTimestamp;
//    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public boolean isDifficult() {
//        return difficult;
//    }
//
//    public void setDifficult(boolean difficult) {
//        this.difficult = difficult;
//    }
//
//    public boolean isModerate() {
//        return moderate;
//    }
//
//    public void setModerate(boolean moderate) {
//        this.moderate = moderate;
//    }
//
//    public boolean isEasy() {
//        return easy;
//    }
//
//    public void setEasy(boolean easy) {
//        this.easy = easy;
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
