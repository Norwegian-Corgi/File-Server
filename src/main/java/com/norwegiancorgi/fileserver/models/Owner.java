package com.norwegiancorgi.fileserver.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Owner {

    @Id
    @SequenceGenerator(name = "ownerUuid", sequenceName = "ownerUuid", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ownerUuid")
    private Long id;
    private String name;
    @Column(
            unique = true
    )
    private String email;
    private String password;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Filez> files;

    /**
     * Constructors
     */
    public Owner() {
    }

    public Owner(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Owner(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Filez> getFiles() {
        return files;
    }

    public void setFiles(Set<Filez> files) {
        this.files = files;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", files=" + files +
                '}';
    }
}
