package com.example.tss.service.impl;

import com.example.tss.dto.BookMarkDto;
import com.example.tss.dto.CircularDto;
import com.example.tss.entity.Circular;
import com.example.tss.entity.CircularBookMark;
import com.example.tss.entity.User;
import com.example.tss.repository.CircularBookMarkRepository;
import com.example.tss.service.CircularBookMarkService;
import com.example.tss.service.CircularService;
import com.example.tss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CircularBookMarkServiceImpl implements CircularBookMarkService {
    private final CircularBookMarkRepository circularBookMarkRepository;
    private final CircularService circularService;
    private final UserService userService;

    @Override
    public BookMarkDto bookMarkCircular(Long circularId, Principal principal) {
        CircularDto circular = circularService.getCircular(circularId).orElseThrow();
        User user = userService.getUser(principal).orElseThrow();
        Optional<CircularBookMark> bookMark = circularBookMarkRepository.findByUserIdAndCircularId(user.getId(), circular.getId());
        ModelMapper mapper = new ModelMapper();
        if (bookMark.isEmpty()) {
            CircularBookMark circularBookMark = CircularBookMark.builder()
                    .user(mapper.map(user, User.class))
                    .circular(mapper.map(circular, Circular.class))
                    .bookMarked(true)
                    .bookMarkedAt(new Timestamp(System.currentTimeMillis()))
                    .build();
            CircularBookMark savedBookmark = circularBookMarkRepository.save(circularBookMark);
            return mapper.map(savedBookmark, BookMarkDto.class);
        }
        CircularBookMark circularBookMark = bookMark.get();
        Boolean bookMarked = circularBookMark.getBookMarked();
        if (bookMarked != null && bookMarked) {
            throw new RuntimeException();
        }
        circularBookMark.setBookMarked(true);
        circularBookMark.setBookMarkedAt(new Timestamp(System.currentTimeMillis()));
        CircularBookMark savedBookmark = circularBookMarkRepository.save(circularBookMark);
        return mapper.map(savedBookmark, BookMarkDto.class);
    }
}
