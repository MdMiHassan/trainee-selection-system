package com.example.tss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmitCardInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Circular circular;
    private String companyName;
    private String companyAddress;
    @Column(length = 1000)
    private String instructions;
    private String authorityName;
    private String division;
    private String location;
    private Date examDate;
    private String time;
    @ManyToOne
    private Resource companyLogoLeft;
    @ManyToOne
    private Resource companyLogoRight;
    @ManyToOne
    private Resource authoritySignatureImage;
}
