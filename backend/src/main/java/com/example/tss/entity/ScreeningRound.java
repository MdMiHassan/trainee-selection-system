package com.example.tss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Circular circular;
    private Integer serialNo;
    private String title;
    private String description;
    private Double maxMark;
    private Double passMark;
    private Timestamp examTime;
    private Timestamp reportingTime;
    private Boolean requiredAdmitCard;
    private String location;
}
