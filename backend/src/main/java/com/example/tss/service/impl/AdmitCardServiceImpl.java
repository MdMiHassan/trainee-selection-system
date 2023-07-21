package com.example.tss.service.impl;

import com.example.tss.admit.AdmitCardMoldFactory;
import com.example.tss.constants.ResourceType;
import com.example.tss.entity.*;
import com.example.tss.exception.AdmitCardGenerationFailedException;
import com.example.tss.repository.*;
import com.example.tss.service.AdmitCardService;
import com.example.tss.service.ResourceService;
import com.example.tss.util.CodeGenerator;
import com.example.tss.util.SystemUtils;
import com.google.zxing.WriterException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdmitCardServiceImpl implements AdmitCardService {
    private final ResourceRepository resourceRepository;
    private final ApplicantProfileRepository applicantProfileRepository;
    private final UserRepository userRepository;
    private final CircularRepository circularRepository;
    private final AdmitCardInformationRepository admitCardInformationRepository;
    private final ApplicationRepository applicationRepository;
    private final ScreeningRoundRepository screeningRoundRepository;
    private final AdmitCardMoldFactory admitCardMoldFactory;
    private final CodeGenerator codeGenerator;
    private final ResourceService resourceService;

    @Override
    public ResponseEntity<?> retrieveAdmit(Long id) {
        return resourceService.getByIdAndResourceType(id, ResourceType.ADMITCARD);
    }

    @Override
    @Transactional
    public ResponseEntity<?> downloadAdmit(Long circularId, Long roundId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new AdmitCardGenerationFailedException("User Doesn't Exists"));
        ApplicantProfile applicantProfile = applicantProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AdmitCardGenerationFailedException("Applicant Doesn't Exists"));
        Circular circular = circularRepository.findById(circularId)
                .orElseThrow(() -> new AdmitCardGenerationFailedException("Circular Doesn't Exists"));
        ScreeningRound screeningRound = screeningRoundRepository.findById(roundId)
                .orElseThrow(() -> new AdmitCardGenerationFailedException("Screening Doesn't Exists"));
        Application application = applicationRepository.findByCircularIdAndCurrentRoundIdAndApplicantId(circularId, roundId, applicantProfile.getId())
                .orElseThrow(() -> new AdmitCardGenerationFailedException("User Doesn't Apply"));
        Resource applicantAdmit = application.getAdmit();
        if (applicantAdmit != null) {
            return buildResourceResponse(applicantAdmit);
        }
        try {
            Resource admit = resourceRepository.save(Resource.builder().build());
            AdmitCardInformation admitCardInformation = admitCardInformationRepository.findByCircularId(circular.getId()).orElseThrow();

            byte[] companyLogoLeft = admitCardInformation.getCompanyLogoLeft().getFileData();
            byte[] companyLogoRight = admitCardInformation.getCompanyLogoRight().getFileData();
            byte[] applicantPhoto = application.getProfileImage().getFileData();
            byte[] applicantSignature = application.getApplicantSignature().getFileData();
            byte[] authoritySignature = admitCardInformation.getAuthoritySignatureImage().getFileData();
            byte[] qrCode = getQrCodeByte(admit.getId());
            byte[] barCode = getBarcodeByte(application.getId());

            String companyName = admitCardInformation.getCompanyName();
            String companyAddress = admitCardInformation.getCompanyAddress();
            String examName = screeningRound.getTitle();

            LinkedHashMap<String, String> basicInfo = new LinkedHashMap<>();
            basicInfo.put("Applicant's Name", application.getFirstName() + " " + application.getLastName());
            basicInfo.put("Applicant's Id", application.getId().toString());
            basicInfo.put("Phone No.", application.getPhone());
            basicInfo.put("Email", application.getEmail());
            basicInfo.put("Position", circular.getTitle());
            basicInfo.put("Degree", application.getDegreeName());
            basicInfo.put("CGPA", application.getCgpa().toString());
            basicInfo.put("Institution", application.getInstitutionName());
            basicInfo.put("Date of Birth", application.getDateOfBirth().toString());
            basicInfo.put("Exam time", screeningRound.getExamTime().toString());
            basicInfo.put("Reporting time", screeningRound.getReportingTime().toString());
            basicInfo.put("Exam Date", screeningRound.getExamTime().toString());
            basicInfo.put("Exam Location", screeningRound.getLocation());

            String[] instructionStrings = admitCardInformation.getInstructions().split("\n");
            List<String> instructions = Arrays.asList(instructionStrings);

            String admitCardHtml = admitCardMoldFactory.getAdmitCardMold()
                    .companyName(companyName)
                    .companyAddress(companyAddress)
                    .examName(examName)
                    .companyLogoLeft(companyLogoLeft)
                    .companyLogoRight(companyLogoRight)
                    .barCode(barCode)
                    .applicantPhoto(applicantPhoto)
                    .applicantSignature(applicantSignature)
                    .qrCode(qrCode)
                    .basicInfo(basicInfo)
                    .instructions(instructions)
                    .authoritySignature(authoritySignature)
                    .forgeAdmitCard().getAdmitHTML();

            byte[] admitCardData = convertHtmlToPdf(admitCardHtml);
            admit.setFileData(admitCardData);
            admit.setOwner(user);
            admit.setFileFormat("pdf");
            admit.setResourceType(ResourceType.ADMITCARD);
            admit.setFileName(admit.getId() + ".pdf");
            admit.setFileDelete(false);
            admit.setDeletedByUser(false);
            admit.setFileRead(false);
            admit.setFileRead(true);
            admit.setUploadAt(SystemUtils.getCurrentTimeStamp());

            Resource savedAdmit = resourceRepository.save(admit);
            return buildResourceResponse(savedAdmit);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private byte[] convertHtmlToPdf(String html) throws IOException {
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        builder.toStream(pdfOutputStream);
        builder.run();
        return pdfOutputStream.toByteArray();
    }

    private ResponseEntity<?> buildResourceResponse(Resource admit) {
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(admit.getFileName());
        if (mediaType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
        return ResponseEntity.ok()
                .contentType(mediaType.get())
                .body(admit.getFileData());
    }

    private byte[] getQrCodeByte(Long admitId) throws IOException, WriterException {
        ByteArrayOutputStream bios = new ByteArrayOutputStream();
        BufferedImage qrCodeImage = codeGenerator.generateQRCodeImage("/admits/verify/" + admitId, 300, 300);
        ImageIO.write(qrCodeImage, "png", bios);
        return bios.toByteArray();
    }

    private byte[] getBarcodeByte(Long applicantId) throws IOException, WriterException {
        ByteArrayOutputStream bios = new ByteArrayOutputStream();
        BufferedImage barCodeImage = codeGenerator.generateBarcodeImage(applicantId.toString(), 300, 100);
        ImageIO.write(barCodeImage, "png", bios);
        return bios.toByteArray();
    }
}
