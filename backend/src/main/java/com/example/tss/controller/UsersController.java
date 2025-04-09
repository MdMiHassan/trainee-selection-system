package com.example.tss.controller;

import com.example.tss.dto.BookMarkDto;
import com.example.tss.service.CircularBookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final CircularBookMarkService circularBookMarkService;

    @PostMapping("/applicants/bookmarks")
    public ResponseEntity<?> bookMarkCircular(@RequestParam Long circular, Principal principal) {
        BookMarkDto bookMarkDto = circularBookMarkService.bookMarkCircular(circular, principal);
        return ResponseEntity.ok(bookMarkDto);
    }
}
