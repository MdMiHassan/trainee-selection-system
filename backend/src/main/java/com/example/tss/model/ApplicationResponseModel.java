package com.example.tss.model;

import com.example.tss.dto.ScreeningRoundMarkDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseModel {
    private Long id;
    private Long uid;
    private String name;
    private String gender;
    private Date dateOfBirth;
    private String degreeName;
    private String institutionName;
    private Float cgpa;
    private Date passingYear;
    private String presentAddress;
    private String phone;
    private String email;
    private Long profileImageId;
    private Long resumeId;
    private List<ScreeningRoundMarkDto> roundMarks;
}
