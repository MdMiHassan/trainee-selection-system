package com.example.tss.util.admit;

import java.util.LinkedHashMap;
import java.util.List;

public interface AdmitCardMold {
    String getAdmitHTML();

    AdmitCardMold forgeAdmitCard() throws Exception;

    AdmitCardMold companyName(String companyName);

    AdmitCardMold examName(String examName);

    AdmitCardMold companyAddress(String companyAddress);

    AdmitCardMold authorityName(String authorityName);

    AdmitCardMold authorityDesignation(String authorityDesignation);

    AdmitCardMold barCode(byte[] barCode);

    AdmitCardMold qrCode(byte[] qrCode);

    AdmitCardMold applicantPhoto(byte[] applicantPhoto);

    AdmitCardMold companyLogoLeft(byte[] companyLogoLeft);

    AdmitCardMold companyLogoRight(byte[] companyLogoRight);

    AdmitCardMold applicantSignature(byte[] applicantSignature);

    AdmitCardMold authoritySignature(byte[] authoritySignature);

    AdmitCardMold instructions(List<String> instructions);

    AdmitCardMold basicInfo(LinkedHashMap<String, String> basicInfo);
}
