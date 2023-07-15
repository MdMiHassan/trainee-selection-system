package com.example.tss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String email;
    private String password;
    private Timestamp expireAt;
    private Long circularId;
    private Long roundId;
    private String gender;
    private String division;
    private String designation;
    private String empId;
    private String phone;
    private String firstName;
    private String lastName;
    private List<Long> applications;
}
