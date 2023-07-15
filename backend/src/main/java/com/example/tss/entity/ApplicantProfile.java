package com.example.tss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String degreeName;
    private String institutionName;
    private Float cgpa;
    private Date passingYear;
    private String presentAddress;
    private String phone;
    @OneToOne
    private Resource profileImage;
    @OneToOne
    private Resource resume;
}
