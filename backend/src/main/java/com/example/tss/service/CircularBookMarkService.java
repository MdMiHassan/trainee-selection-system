package com.example.tss.service;

import com.example.tss.dto.BookMarkDto;

import java.security.Principal;

public interface CircularBookMarkService {
    BookMarkDto bookMarkCircular(Long circularId, Principal principal);
}
