package com.example.xedd.model;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "product")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    //    @NotNull(message = "Product name is required.")
//    @Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters.")
    @Column(name = "name", nullable = false)
    private String name;

    //    @NotNull(message = "Product description is required.")
    //@Size(min = 10, max = 1000, message = "Product description must be between 10 and 1000 characters.")
    @Column(name = "description", nullable = false)
    private String description;

    private String byUser;

    private boolean favourite;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    public Product() {}

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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", byUser='" + byUser + '\'' +
                ", favourite=" + favourite +
                ", difficulty=" + difficulty +
                ", image=" + Arrays.toString(image) +
                ", createDate=" + createDate +
                '}';
    }
}