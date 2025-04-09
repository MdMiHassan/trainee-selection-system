package com.example.tss.service;

import com.example.tss.dto.NoticeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface NoticeService {
    ResponseEntity<?> getAllNotices(Pageable page);

    NoticeDto postNotice(Principal principal, NoticeDto noticeDto);

    NoticeDto getNotice(Long noticeId);

    NoticeDto updateNotice(Long noticeId, NoticeDto noticeDto);

    void deleteNotice(Long noticeId);
}
