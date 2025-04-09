package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.dto.NoticeDto;
import com.example.tss.entity.Notice;
import com.example.tss.entity.Resource;
import com.example.tss.entity.User;
import com.example.tss.exception.UnauthorizedAccessException;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserService userService;
    private final ResourceService resourceService;

    @Override
    public ResponseEntity<?> getAllNotices(Pageable page) {
        Page<NoticeDto> notices = noticeRepository.findAll(page).map(NoticeMapper::mapToNoticeDto);
        return ResponseEntity.ok(notices);
    }

    @Override
    @Transactional
    public NoticeDto postNotice(Principal principal, NoticeDto noticeDto) {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new UnauthorizedAccessException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("Only admins can post notices");
        }
        Resource attachment = resourceService.getResourceById(principal, noticeDto.getAttachmentId());
        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .details(noticeDto.getDetails())
                .attachment(attachment)
                .postedBy(user)
                .postedAt(SystemUtils.getCurrentTimeStamp())
                .build();

        Notice savedNotice = noticeRepository.save(notice);
        return NoticeMapper.mapToNoticeDto(savedNotice);
    }

    @Override
    public NoticeDto getNotice(Long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        return notice.map(NoticeMapper::mapToNoticeDto).orElseThrow();
    }

    @Override
    @Transactional
    public NoticeDto updateNotice(Long noticeId, NoticeDto noticeDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }
}
