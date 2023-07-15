package com.example.tss.model;

import com.example.tss.dto.ApplicantProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyRequestModel {
    ApplicantProfileDto applicationProfile;
}
