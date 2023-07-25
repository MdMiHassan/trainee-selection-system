package com.example.tss.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarksDto {
    @NotNull(message = "Candidate Id is required")
    private Long candidateUid;
    @NotNull(message = "Mark is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Marks must be greater than 0")
    private Double totalMarks;
}
