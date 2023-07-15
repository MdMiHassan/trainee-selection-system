package com.example.tss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarksDto {
    private Long circularId;
    private Long candidateUid;
    private Double totalMarks;
}
