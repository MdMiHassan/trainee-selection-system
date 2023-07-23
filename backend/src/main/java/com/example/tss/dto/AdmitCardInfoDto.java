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
public class AdmitCardInfoDto {
    private String companyName;
    private String companyAddress;
    private String instructions;
    private String authorityName;
    private String division;
    private Long companyLogoLeftId;
    private Long companyLogoRightId;
    private Long authoritySignatureImageId;
    private String location;
    private Date examDate;
    private String time;
}
