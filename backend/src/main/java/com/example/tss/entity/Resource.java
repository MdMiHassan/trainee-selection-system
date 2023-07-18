package com.example.tss.entity;

import com.example.tss.constants.ResourceType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;
    @ManyToOne
    private User owner;
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;
    private Boolean fileRead;
    private Boolean fileWrite;
    private Boolean fileDelete;
    private Boolean deletedByUser;
    private Boolean deletedByUserAt;
    private String fileName;
    private String fileFormat;
    private Timestamp uploadAt;
}
