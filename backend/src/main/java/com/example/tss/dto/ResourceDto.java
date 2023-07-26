package com.example.tss.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {
    private Long id;
    private byte[] fileData;
    private Long ownerId;
    private String ownerUsername;
    private String resourceType;
    private Boolean fileRead;
    private Boolean fileWrite;
    private Boolean fileDelete;
    private Boolean deletedByUser;
    private Boolean deletedByUserAt;
    private String fileName;
    private String fileFormat;
    private Timestamp uploadAt;
}
