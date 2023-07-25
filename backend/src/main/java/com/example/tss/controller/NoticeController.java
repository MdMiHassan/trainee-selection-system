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
    public ResponseEntity<?> getAllNotices(Pageable page){
       return noticeService.getAllNotices(page);
    }
    @PostMapping
    public ResponseEntity<?> postNotice(Principal principal, @RequestBody NoticeDto noticeDto){
        return noticeService.postNotice(principal,noticeDto);
    }
    @GetMapping("/{noticeId}")
    public ResponseEntity<?> getNotice(Long noticeId){
        return noticeService.getNotice(noticeId);
    }
    @PostMapping("/{noticeId}")
    public ResponseEntity<?> updateNotice(Long noticeId, @RequestBody NoticeDto noticeDto){
        return noticeService.updateNotice(noticeId,noticeDto);
    }
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> postNotice(Long noticeId){
        return noticeService.deleteNotice(noticeId);
    }
}
