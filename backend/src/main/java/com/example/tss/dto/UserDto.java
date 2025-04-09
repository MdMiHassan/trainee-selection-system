package com.example.tss.dto;

import com.example.tss.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private Boolean emailVerified;
    private String password;
    private Role role;
    private Boolean locked;
    private Boolean enabled;
    private Date registeredAt;
    private Timestamp expiredAt;
}
