package com.norwegiancorgi.fileserver.internal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Filez {

    @Id
    @GeneratedValue
    @Column(name = "uuid", nullable = false)
    private UUID id;
    @NonNull
    @Column(name = "name", nullable = false)
    private String name;
    @NonNull
    @Column(name = "content_type", nullable = false)
    private String contentType;
    @NonNull
    @Column(name = "path", nullable = false)
    private String path;
    @NonNull
    @Column(name = "downloads", nullable = false)
    private Integer numberOfDownloads;
    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    @NonNull
    @JsonIgnore
    private Userz userz;
}
