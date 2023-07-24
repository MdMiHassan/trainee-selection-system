package com.example.tss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluatorDto {
    private Long id;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private Timestamp expireAt;
    @NotNull(message = "Circular ID is required")
    private Long circularId;
    @NotNull(message = "Round ID is required")
    private Long roundId;
    private String gender;
    private String division;
    private String designation;
    private String empId;
    private String phone;
    @NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;
    @NotNull(message = "Application IDs is required")
    private List<Long> applications;
}
