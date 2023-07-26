package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.dto.NoticeDto;
import com.example.tss.entity.Notice;
import com.example.tss.entity.Resource;
import com.example.tss.entity.User;
import com.example.tss.mapper.NoticeMapper;
import com.example.tss.repository.NoticeRepository;
import com.example.tss.service.NoticeService;
import com.example.tss.service.ResourceService;
import com.example.tss.service.UserService;
import com.example.tss.util.SystemUtils;
import jakarta.transaction.Transactional;
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
    private final UserService userService;
    private final ResourceService resourceService;

    @Override
    public ResponseEntity<?> getAllNotices(Pageable page) {
        Page<NoticeDto> noticeDtos = noticeRepository.findAll(page).map(NoticeMapper::mapToNoticeDto);
        return ResponseEntity.ok(noticeDtos);
    }

    @Override
    @Transactional
    public ResponseEntity<?> postNotice(Principal principal, NoticeDto noticeDto) {
        String email = principal.getName();
        User user = userService.getByEmail(email).orElseThrow();
        if (!user.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.badRequest().build();
        }
        Resource attachment = resourceService.getResourceById(principal, noticeDto.getAttachmentId());
        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .details(noticeDto.getDetails())
                .attachment(attachment)
                .postedBy(user)
                .postedAt(SystemUtils.getCurrentTimeStamp())
                .build();
        Notice saved = noticeRepository.save(notice);
        return ResponseEntity.ok(NoticeMapper.mapToNoticeDto(saved));
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
