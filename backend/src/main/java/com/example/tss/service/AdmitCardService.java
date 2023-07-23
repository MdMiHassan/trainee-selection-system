package com.example.tss.service;

import com.example.tss.dto.AdmitCardInfoDto;
import com.example.tss.entity.Application;
import com.example.tss.entity.Circular;
import com.example.tss.entity.ScreeningRound;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface AdmitCardService {
    ResponseEntity<?> retrieveAdmit(Long id);

    boolean generateAdmitCard(Application application, ScreeningRound screeningRound, Circular circular);

    ResponseEntity<?> saveAdmitInfo(Long circularId, AdmitCardInfoDto admitCardInfoDto);

    ResponseEntity<?> getAdmitId(Principal principal,Long circularId);
}
