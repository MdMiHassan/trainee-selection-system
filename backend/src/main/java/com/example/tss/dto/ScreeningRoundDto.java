package com.example.tss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningRoundDto {
    private Long roundId;
    private Long circularId;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotBlank(message = "Serial No is required")
    private Integer serialNo;
    private Double maxMark;
    private Double passMark;
    private Timestamp examTime;
    private String examLocation;
    private Boolean requiredAdmitCard;
}
