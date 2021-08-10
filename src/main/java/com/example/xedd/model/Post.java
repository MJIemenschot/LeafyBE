package com.example.xedd.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Post {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private String fileName;
//    private String title;
//    private String description;
//    private String fileType;
//    @Lob
//    private byte[] fileData;
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(String fileType) {
//        this.fileType = fileType;
//    }
//
//    public byte[] getFileData() {
//        return fileData;
//    }
//
//    public void setFileData(byte[] fileData) {
//        this.fileData = fileData;
    //  }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    private String category;

    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    private String byUser;

    private boolean favourite;



    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    public Post() {
    }


    public Post(Long id,
                String name,
                String description,
                byte[] image,
                String byUser,
                boolean favourite,
                String category,
                Date createDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.byUser = byUser;
        this.favourite = favourite;
        this.category = category;
        this.createDate = createDate;

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getByUser() { return byUser; }
    public void setByUser(String byUser) { this.byUser = byUser; }
    public boolean isFavourite() { return favourite; }
    public void setFavourite(boolean favourite) { this.favourite = favourite; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    @Override
    public String toString() {
        return "Post[id=" + id + ", name=" + name + ", description=" + description + ", category=" + category + ", image="
                + Arrays.toString(image) + ", createDate=" + createDate + "]";
    }
}
