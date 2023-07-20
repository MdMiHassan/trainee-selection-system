package com.example.tss.service.impl;

import com.example.tss.repository.RoundMarkRepository;
import com.example.tss.service.ScreeningRoundMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreeningRoundMarkServiceImpl implements ScreeningRoundMarkService {
    private  final RoundMarkRepository roundMarkRepository;

}
