package com.example.tss.controller;

import com.example.tss.dto.NoticeDto;
import com.example.tss.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<?> getAllNotices(Pageable page) {
        return noticeService.getAllNotices(page);
    }

    @PostMapping
    public ResponseEntity<?> postNotice(Principal principal, @RequestBody NoticeDto noticeDto) {
        NoticeDto notice = noticeService.postNotice(principal, noticeDto);
        return ResponseEntity.ok(notice);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDto> getNotice(@PathVariable Long noticeId) {
        NoticeDto notice = noticeService.getNotice(noticeId);
        return ResponseEntity.ok(notice);
    }

    @PostMapping("/{noticeId}")
    public ResponseEntity<NoticeDto> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeDto noticeDto) {
        NoticeDto notice = noticeService.updateNotice(noticeId, noticeDto);
        return ResponseEntity.ok(notice);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> postNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok().build();
    }
}
