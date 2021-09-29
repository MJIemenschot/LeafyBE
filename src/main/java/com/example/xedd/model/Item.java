package com.example.xedd.model;

import org.springframework.web.multipart.MultipartFile;

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
    private String latinName;

    //@Lob
    private String description;
    private String toPicture;
    private String fileName;
    private String mediaType;
    //private String picture;
    private String username;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(value = EnumType.STRING)
    private Light light;

    @Enumerated(value = EnumType.STRING)
    private Food food;

    @Enumerated(value = EnumType.STRING)
    private Watering watering;
//
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploaded_date")
    private Date uploadedDate;

//    @Column(name = "uploaded_by_username")
//    @ManyToOne
//    @JoinColumn(name="user_id")
    //private String user;


    //constructor empty

    public Item() {
    }
    //constructor


    public Item(long id,
                String name,
                String latinName,
                String description,
                String toPicture,
                String fileName,
                String mediaType,
                MultipartFile file,
                Date uploadedDate,
                String username,
                Difficulty difficulty,
                Light light,
                Watering watering,
                Food food
    ) {
        this.id = id;
        this.name = name;
        this.latinName = latinName;
        this.description = description;
        this.toPicture = toPicture;
        this.uploadedDate = uploadedDate;
        this.fileName = fileName;
        this.mediaType = mediaType;
        //this.file = file;
        this.username = username;
        this.difficulty = difficulty;
        this.light = light;
        this.food = food;
        this.watering = watering;
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

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
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

//    public MultipartFile getFile() {
//        return file;
//    }
//
//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Watering getWatering() {
        return watering;
    }

    public void setWatering(Watering watering) {
        this.watering = watering;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
