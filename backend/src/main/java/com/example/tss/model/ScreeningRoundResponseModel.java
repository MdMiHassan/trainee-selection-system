package com.example.tss.model;

import com.example.tss.dto.ScreeningRoundDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningRoundResponseModel {
    private Integer count;
    private Integer currentRoundSerialNo;
    private Integer currentRoundId;
    private List<ScreeningRoundDto> rounds;
}
