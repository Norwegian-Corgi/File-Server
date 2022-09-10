package com.norwegiancorgi.fileserver.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "owner"})
public class Filez {

    @Id
    @SequenceGenerator(name = "fileUuid", sequenceName = "fileUuid", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileUuid")
    private Long id;
    private String name;
    private String type;
    private String path;
    private Integer numberOfDownloads;
    private Long size;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    /**
     * Constructors
     */
    public Filez() {
    }

    public Filez(String name, String type, String path, Integer numberOfDownloads, Long size, Owner owner) {
        this.name = name;
        this.type = type;
        this.path = path;
        this.numberOfDownloads = numberOfDownloads;
        this.size = size;
        this.owner = owner;
    }

    public Filez(Long id, String name, String type, String path, Integer numberOfDownloads, Long size, Owner owner) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.path = path;
        this.numberOfDownloads = numberOfDownloads;
        this.size = size;
        this.owner = owner;
    }

    /**
     * Getters and Setters
     */
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setNumberOfDownloads(Integer numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        return "Filez{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", numberOfDownloads=" + numberOfDownloads +
                ", size=" + size +
                '}';
    }
}
