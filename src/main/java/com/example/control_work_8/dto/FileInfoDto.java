package com.example.control_work_8.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto {

    private Long id;
    private String originalName;
    private String contentType;
    private Boolean isPublic;
    private String shareKey;
    private Integer downloadCount;
    private String storedName;
    private Long userId;
}