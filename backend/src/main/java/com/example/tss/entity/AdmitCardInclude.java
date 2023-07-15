package com.example.tss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdmitCardInclude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AdmitCardInformation admitCardInformation;
    private boolean IncludeCompanyName;
    private boolean IncludeCompanyAddress;
    private boolean IncludeInstructions;
    private boolean IncludeAuthorityName;
    private boolean IncludeDivision;
    private boolean IncludeCompanyLogoLeft;
    private boolean IncludeCompanyLogoRight;
    private boolean IncludeAuthoritySignatureImage;
    private boolean IncludeCFirstName;
    private boolean IncludeLastName;
    private boolean IncludeGender;
    private boolean IncludeDateOfBirth;
    private boolean IncludeDegreeName;
    private boolean IncludeInstitutionName;
    private boolean IncludeCgpa;
    private boolean IncludePassingYear;
    private boolean IncludePresentAddress;
    private boolean IncludePhone;
    private boolean IncludeEmail;
    private boolean IncludeProfileImage;
    private boolean IncludeResume;
}
