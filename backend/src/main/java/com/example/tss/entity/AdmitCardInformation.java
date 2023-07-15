package com.example.tss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdmitCardInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Circular circular;
    private String companyName;
    private String companyAddress;
    private String instructions;
    private String authorityName;
    private String division;
    @ManyToOne
    private Resource companyLogoLeft;
    @ManyToOne
    private Resource companyLogoRight;
    @ManyToOne
    private Resource authoritySignatureImage;
}
