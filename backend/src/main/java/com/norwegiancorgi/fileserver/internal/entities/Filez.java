package com.norwegiancorgi.fileserver.internal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
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
    private String userEmail;
}
