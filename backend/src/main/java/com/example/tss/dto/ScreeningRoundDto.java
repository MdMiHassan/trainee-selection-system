package com.example.tss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningRoundDto {
    private Long roundId;
    private Long circularId;
    private String title;
    private String description;
    private Integer serialNo;
    private Double maxMark;
    private Double passMark;
}
