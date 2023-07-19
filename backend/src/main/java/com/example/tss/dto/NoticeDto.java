package com.example.tss.dto;

import com.example.tss.entity.Resource;
import com.example.tss.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private Long id;
    private String title;
    private String details;
    private String postedBy;
    private Timestamp postedAt;
    private Long attachmentId;
}
