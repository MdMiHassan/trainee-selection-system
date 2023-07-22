package com.example.tss.service;

import com.example.tss.entity.Circular;
import com.example.tss.entity.User;

public interface BookMarkCircularService {
    void toggleBookMark(User user, Circular circular);
}
