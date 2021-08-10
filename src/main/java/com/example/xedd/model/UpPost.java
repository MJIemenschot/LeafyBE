//package com.example.xedd.model;
//import java.sql.Timestamp;
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//public class UpPost {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    private String name;
//
//    private String description;
//
//    private String imagePath;
//    private String imageName;
//
//    private String byUser;
//    private String seed;
//    private String ent;
//    private String plant;
//
//    private boolean favourite;
//
//    //@Temporal(TemporalType.TIMESTAMP)
//
//    //private Timestamp createdDate;
//
//    public UpPost() {
//    }
//
//    public UpPost(Long id, String name, String description, String imagePath, String imageName, String user, boolean favourite, String category, Timestamp createdDate) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.imagePath = imagePath;
//        this.imageName = imageName;
//        this.byUser = byUser;
//        this.favourite = favourite;
//        this.seed = seed;
//        //this.createdDate = createdDate;
//    }
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public String getImagePath() { return imagePath; }
//    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
//
//    public String getImageName() {
//        return imageName;
//    }
//
//    public void setImageName(String imageName) {
//        this.imageName = imageName;
//    }
//
//    public String getEnt() {
//        return ent;
//    }
//
//    public void setEnt(String ent) {
//        this.ent = ent;
//    }
//
//    public String getPlant() {
//        return plant;
//    }
//
//    public void setPlant(String plant) {
//        this.plant = plant;
//    }
//
//    public String getByUser() { return byUser; }
//    public void setByUser(String ByUser) { this.byUser = byUser; }
//    public boolean isFavourite() { return favourite; }
//    public void setFavourite(boolean favourite) { this.favourite = favourite; }
//    public String getSeed() { return seed; }
//    public void setSeed(String seed) { this.seed = seed; }
////    public Timestamp getCreatedDate() { return createdDate; }
////    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }
//    @Override
//    public String toString() {
//        return "UpPost[id=" + id + ", name=" + name + ", description=" + description + ", seed=" + seed + ", image="
//                + imagePath +
////                "createdDate=" + createdDate +
//                "]";
//    }
//
//
//}
