package com.example.tss.service.impl;

import com.example.tss.entity.BookMarkCircular;
import com.example.tss.entity.Circular;
import com.example.tss.entity.User;
import com.example.tss.repository.BookMarkCircularRepository;
import com.example.tss.service.BookMarkCircularService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookMarkCircularServiceImpl implements BookMarkCircularService {
    BookMarkCircularRepository bookMarkCircularRepository;
    @Override
    @Transactional
    public void toggleBookMark(User user, Circular circular) {
        Optional<BookMarkCircular> bookMarkCircular=bookMarkCircularRepository.findByUserIdAndCircularId(user.getId(),circular.getId());
        if(bookMarkCircular.isEmpty()){
            bookMarkCircularRepository.save(
                    BookMarkCircular.builder()
                            .user(user)
                            .circular(circular)
                            .build()
            );
        }else{
            bookMarkCircularRepository.delete(bookMarkCircular.get());
        }
    }
}
