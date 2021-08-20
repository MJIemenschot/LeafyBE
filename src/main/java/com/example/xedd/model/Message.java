package com.example.xedd.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String fileName;
    private String title;
    private String description;
    private String mediaType;
    private String location;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploaded_timestmp")
    private Date uploadedTimestamp;

    private String uploadedByUsername;


    public Message() {
    }

    public Message(long id, String fileName, String title, String description, String mediaType, String location, Difficulty difficulty, Date uploadedTimestamp, String uploadedByUsername) {
        this.id = id;
        this.fileName = fileName;
        this.title = title;
        this.description = description;
        this.mediaType = mediaType;
        this.location = location;
        this.difficulty = difficulty;
        this.uploadedTimestamp = uploadedTimestamp;
        this.uploadedByUsername = uploadedByUsername;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

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

    public Date getUploadedTimestamp() {
        return uploadedTimestamp;
    }

    public void setUploadedTimestamp(Date uploadedTimestamp) {
        this.uploadedTimestamp = uploadedTimestamp;
    }

    public String getUploadedByUsername() {
        return uploadedByUsername;
    }

    public void setUploadedByUsername(String uploadedByUsername) {
        this.uploadedByUsername = uploadedByUsername;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
