package com.example.tss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicantProfileDto {
    private Long id;
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
    private String email;
    private Long profileImageId;
    private Long resumeId;
    private String profileImagePath;
    private String resumePath;
}
