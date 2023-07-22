package com.example.tss.dto;

import com.example.tss.constants.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    private Gender gender;

    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @NotBlank(message = "Degree name must not be blank")
    private String degreeName;

    @NotBlank(message = "Institution name must not be blank")
    private String institutionName;

    @DecimalMin(value = "0.0", message = "CGPA must be greater than or equal to 0.0")
    @DecimalMax(value = "4.0", message = "CGPA must be less than or equal to 4.0")
    private Float cgpa;

    @PastOrPresent(message = "Passing year must be in the past or present")
    private Date passingYear;

    @NotBlank(message = "Present address must not be blank")
    private String presentAddress;

    @NotBlank(message = "Phone must not be blank")
    private String phone;

    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Profile image id required")
    private Long profileImageId;

    @NotNull(message = "Resume id required")
    private Long resumeId;

    private String profileImagePath;
    private String resumePath;
}
