package com.example.tss.dto;

import com.example.tss.constants.CareerLevel;
import com.example.tss.constants.TrainingType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CircularDto {
    @NotBlank(message = "Title is required")
    private String title;
    private Date closingDate;
    @NotBlank(message = "Overview is required")
    private String overview;
    private TrainingType trainingType;
    private CareerLevel careerLevel;
    @NotNull(message = "Vacancy is required")
    private Integer vacancy;
    private String requiredEducation;
    private String hiringLocation;
    @NotBlank(message = "Skills are required")
    private String skills;
    @NotBlank(message = "Duties are required")
    private String duties;
    private Float salary;
    private String currency;
    @NotNull(message = "Minimum experience is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum experience must be greater than 0")
    private Double minExp;
    @NotNull(message = "Maximum experience is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum experience must be greater than 0")
    private Double maxExp;
}
