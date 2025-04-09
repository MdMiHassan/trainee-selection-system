package com.example.tss.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailVerificationRequest {
    @NotNull(message = "Email must not be null")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    @NotNull(message = "Email must not be null")
    @NotEmpty(message = "Email must not be empty")
    private String verificationCode;
}
