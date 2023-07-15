package com.example.tss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CircularDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Job type is required")
    private String jobType;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Duties are required")
    private String duties;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    private Float salary;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotNull(message = "Minimum experience is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum experience must be greater than 0")
    private Double minExp;

    @NotNull(message = "Maximum experience is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum experience must be greater than 0")
    private Double maxExp;
}
