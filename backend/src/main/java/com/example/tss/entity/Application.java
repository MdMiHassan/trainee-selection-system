package com.example.tss.entity;

import com.example.tss.constants.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Circular circular;
    @ManyToOne
    private ApplicantProfile applicant;
    @ManyToOne
    private ScreeningRound currentRound;
    @Column(unique = true)
    private Long uniqueIdentifier;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date dateOfBirth;
    private String degreeName;
    private String institutionName;
    private Float cgpa;
    private Date passingYear;
    private String presentAddress;
    private String phone;
    private String email;
    @ManyToOne
    private Resource profileImage;
    @ManyToOne
    private Resource resume;
    @ManyToOne
    private Resource applicantSignature;
    @OneToOne
    private Resource admit;
    private Timestamp appliedAt;
}
