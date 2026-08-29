package com.example.control_work_8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private String storedName;
    private String contentType;
    private Boolean isPublic;
    private String shareKey;
    private Integer downloadCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
