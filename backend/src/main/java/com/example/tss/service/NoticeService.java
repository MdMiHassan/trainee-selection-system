package com.example.tss.service;

import com.example.tss.dto.NoticeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface NoticeService {
    ResponseEntity<?> getAllNotices(Pageable page);

    ResponseEntity<?> postNotice(Principal principal, NoticeDto noticeDto);

    ResponseEntity<?> getNotice(Long noticeId);

    ResponseEntity<?> updateNotice(Long noticeId, NoticeDto noticeDto);

    ResponseEntity<?> deleteNotice(Long noticeId);
}
