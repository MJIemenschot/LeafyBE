package com.example.xedd.model;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;

    private String toImage;

    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE)
    private byte[] image;

    private String fileType;

    private String byUser;

    private boolean favourite;

        @Enumerated(value = EnumType.STRING)
        private Difficulty difficulty;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    public Post() {
    }

    public Post(Long id, String name, String description, String toImage, byte[] image, String fileType, String byUser, boolean favourite, Difficulty difficulty, Date createDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.toImage = toImage;
        this.image = image;
        this.fileType = fileType;
        this.byUser = byUser;
        this.favourite = favourite;
        this.difficulty = difficulty;
        this.createDate = createDate;
    }

    public Long getId() {
                return id;
        }

        public void setId(Long id) {
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

        public String getToImage() {
                return toImage;
        }

        public void setToImage(String category) {
                this.toImage = category;
        }

        public byte[] getImage() {
                return image;
        }

        public void setImage(byte[] image) {
                this.image = image;
        }

        public String getByUser() {
                return byUser;
        }

        public void setByUser(String byUser) {
                this.byUser = byUser;
        }

        public boolean isFavourite() {
                return favourite;
        }

        public void setFavourite(boolean favourite) {
                this.favourite = favourite;
        }

        public Difficulty getDifficulty() {
                return difficulty;
        }

        public void setDifficulty(Difficulty difficulty) {
                this.difficulty = difficulty;
        }

        public Date getCreateDate() {
                return createDate;
        }

        public void setCreateDate(Date createDate) { this.createDate = createDate;
        }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
