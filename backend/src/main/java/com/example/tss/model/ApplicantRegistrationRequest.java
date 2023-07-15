package com.example.tss.model;

import com.example.tss.entity.Circular;
import com.example.tss.entity.Resource;
import com.example.tss.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantRegistrationRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String degreeName;
    private String institutionName;
    private Float gcpa;
    private Date passingYear;
    private String presentAddress;
    private String phone;
    private String email;
    private String password;
    private Long profileImageId;
    private Long resumeId;
}
