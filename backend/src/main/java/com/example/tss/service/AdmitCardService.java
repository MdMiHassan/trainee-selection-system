package com.example.tss.service;

import com.example.tss.dto.AdmitCardInfoDto;
import com.example.tss.dto.ResourceDto;
import com.example.tss.entity.Application;
import com.example.tss.entity.Circular;
import com.example.tss.entity.ScreeningRound;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface AdmitCardService {
    ResourceDto retrieveAdmit(Long id);

    boolean generateAdmitCard(Application application, ScreeningRound screeningRound, Circular circular);

    AdmitCardInfoDto saveAdmitInfo(Long circularId, AdmitCardInfoDto admitCardInfoDto);
}
