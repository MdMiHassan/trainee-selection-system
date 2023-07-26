package com.example.tss.entity;

import com.example.tss.constants.CareerLevel;
import com.example.tss.constants.TrainingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Circular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 500)
    private String overview;
    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;
    private Date closingDate;
    private String requiredEducation;
    private String hiringLocation;
    @Enumerated(EnumType.STRING)
    private CareerLevel careerLevel;
    private Integer vacancy;
    @Column(length = 1000)
    private String skills;
    @Column(length = 1000)
    private String duties;
    private Float salary;
    private String currency;
    private Float minExp;
    private Float maxExp;
}
