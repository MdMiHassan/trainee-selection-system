package com.example.tss.service.impl;

import com.example.tss.dto.NoticeDto;
import com.example.tss.mapper.NoticeMapper;
import com.example.tss.repository.NoticeRepository;
import com.example.tss.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Override
    public ResponseEntity<?> getAllNotices(Pageable page) {
        Page<NoticeDto> noticeDtos = noticeRepository.findAll(page).map(NoticeMapper::mapToNoticeDto);
        return ResponseEntity.ok(noticeDtos);
    }

    @Override
    public ResponseEntity<?> postNotice(Principal principal, NoticeDto noticeDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getNotice(Long noticeId) {
        NoticeDto noticeDto = noticeRepository.findById(noticeId).map(NoticeMapper::mapToNoticeDto)
                .orElseThrow();
        return ResponseEntity.ok(noticeDto);
    }

    @Override
    public ResponseEntity<?> updateNotice(Long noticeId, NoticeDto noticeDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteNotice(Long noticeId) {
        return null;
    }
}
