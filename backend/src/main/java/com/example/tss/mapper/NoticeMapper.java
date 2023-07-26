package com.example.tss.mapper;

import com.example.tss.dto.NoticeDto;
import com.example.tss.entity.Notice;

public class NoticeMapper {
    public static NoticeDto mapToNoticeDto(Notice notice) {
        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .details(notice.getDetails())
                .attachmentId(notice.getAttachment().getId())
                .postedAt(notice.getPostedAt())
                .build();
    }
}
