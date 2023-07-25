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
public class ApplicationInfoDto {
    private Long currentRoundAdmitId;
    private Long applicationId;
    private Long circularId;
    private Integer currentRoundSerialNo;
    private Boolean requiredAdmitCard;
}
