package com.example.xedd.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="plants")
public class Plant {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "name")
    private String name;

    private String latinName;

    @Column(name = "description", columnDefinition = "TEXT")
    //@Lob geeft een nummer in de database:((
    private String description;

    @Column(name = "care", columnDefinition = "TEXT")
    private String care;

    @Column(name = "potting", columnDefinition = "TEXT")
    private String potting;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "location")
    private String location;

    @Column(name = "download_uri")
    private String downloadUri;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(value = EnumType.STRING)
    private Light light;

    @Enumerated(value = EnumType.STRING)
    private Food food;

    @Enumerated(value = EnumType.STRING)
    private Watering watering;

    @Column(name = "uploaded_date")
    private Date uploadedDate;


    @Column(name = "uploaded_by_username")
    private String uploadedByUsername;


    public Plant() {
    }

    public Plant(long id,
                 String fileName,
                 String name,
                 String latinName,
                 String description,
                 String care,
                 String potting,
                 String mediaType,
                 String location,
                 String downloadUri,
                 Difficulty difficulty,
                 Light light, Food food,
                 Watering watering,
                 Date uploadedDate,
                 String uploadedByUsername) {
        this.id = id;
        this.fileName = fileName;
        this.name = name;
        this.latinName = latinName;
        this.description = description;
        this.care = care;
        this.potting = potting;
        this.mediaType = mediaType;
        this.location = location;
        this.downloadUri = downloadUri;
        this.difficulty = difficulty;
        this.light = light;
        this.food = food;
        this.watering = watering;
        this.uploadedDate = uploadedDate;
        this.uploadedByUsername = uploadedByUsername;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
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

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getPotting() {
        return potting;
    }

    public void setPotting(String potting) {
        this.potting = potting;
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

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
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

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getUploadedByUsername() {
        return uploadedByUsername;
    }

    public void setUploadedByUsername(String uploadedByUsername) {
        this.uploadedByUsername = uploadedByUsername;
    }

}