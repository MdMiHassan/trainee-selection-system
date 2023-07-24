package com.example.tss.service.impl;

import com.example.tss.dto.AdmitCardInfoDto;
import com.example.tss.dto.ApplicationDto;
import com.example.tss.dto.ApplicationInfoDto;
import com.example.tss.exception.AdmitCardGenerationException;
import com.example.tss.service.UserService;
import com.example.tss.util.admit.AdmitCardMoldFactory;
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
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdmitCardServiceImpl implements AdmitCardService {
    private final ResourceLoader resourceLoader;
    private final ResourceRepository resourceRepository;
    private final CircularRepository circularRepository;
    private final AdmitCardInformationRepository admitCardInformationRepository;
    private final ApplicationRepository applicationRepository;
    private final AdmitCardMoldFactory admitCardMoldFactory;
    private final CodeGenerator codeGenerator;
    private final ResourceService resourceService;
    private final UserService userService;
    private final ApplicantProfileRepository applicantProfileRepository;

    @Override
    public ResponseEntity<?> retrieveAdmit(Long id) {
        return resourceService.getByIdAndResourceType(id, ResourceType.ADMITCARD);
    }


    @Override
    public boolean generateAdmitCard(Application application, ScreeningRound screeningRound, Circular circular) {
        try {
            Resource admit = resourceRepository.save(Resource.builder().build());
            AdmitCardInformation admitCardInformation = admitCardInformationRepository.findByCircularId(circular.getId()).orElseThrow(AdmitCardGenerationException::new);
            byte[] companyLogoLeft = admitCardInformation.getCompanyLogoLeft().getFileData();
            byte[] companyLogoRight = admitCardInformation.getCompanyLogoRight().getFileData();
            byte[] applicantPhoto = application.getProfileImage().getFileData();
            byte[] authoritySignature = admitCardInformation.getAuthoritySignatureImage().getFileData();
            byte[] qrCode = getQrCodeByte(admit.getId());
            byte[] barCode = getBarcodeByte(application.getId());

            String companyName = admitCardInformation.getCompanyName();
            String companyAddress = admitCardInformation.getCompanyAddress();
            String examName = screeningRound.getTitle();
            String authorityName=admitCardInformation.getAuthorityName();
            String examLocation=admitCardInformation.getLocation();

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
            basicInfo.put("Exam time", admitCardInformation.getTime());
            basicInfo.put("Exam Date", admitCardInformation.getExamDate().toString());
            basicInfo.put("Exam Location", examLocation);
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
                    .qrCode(qrCode)
                    .basicInfo(basicInfo)
                    .instructions(instructions)
                    .authoritySignature(authoritySignature)
                    .authorityName(authorityName)
                    .forgeAdmitCard().getAdmitHTML();
            byte[] admitCardData = convertHtmlToPdf(admitCardHtml);
            admit.setFileData(admitCardData);
            admit.setFileFormat("pdf");
            admit.setResourceType(ResourceType.ADMITCARD);
            admit.setFileName(admit.getId() + ".pdf");
            admit.setFileDelete(false);
            admit.setDeletedByUser(false);
            admit.setFileRead(false);
            admit.setFileRead(true);
            admit.setUploadAt(SystemUtils.getCurrentTimeStamp());
            Resource savedAdmit = resourceRepository.save(admit);
            application.setAdmit(savedAdmit);
            applicationRepository.save(application);
            return true;
        } catch (Exception e) {
            throw new AdmitCardGenerationException();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> saveAdmitInfo(Long circularId, AdmitCardInfoDto admitCardInfoDto) {
        try {
            byte[] logoLeftContent =resourceLoader.getResource("classpath:static/logo/bjitacademylogo.png")
                    .getInputStream().readAllBytes();
            byte[] logoRightContent =resourceLoader.getResource("classpath:static/logo/bjitlogo.png")
                    .getInputStream().readAllBytes();
            Resource logoleftResource = Resource.builder().fileData(logoLeftContent).build();
            Resource logoRightResource = Resource.builder().fileData(logoRightContent).build();
            Resource signature = resourceRepository.findById(admitCardInfoDto.getAuthoritySignatureImageId()).orElseThrow();
            Circular circular = circularRepository.findById(circularId).orElseThrow();
            resourceRepository.save(logoleftResource);
            resourceRepository.save(logoRightResource);
            AdmitCardInformation admitCardInformation = AdmitCardInformation.builder()
                    .circular(circular)
                    .companyLogoLeft(logoleftResource)
                    .companyLogoRight(logoRightResource)
                    .companyName("BJIT ACADEMY")
                    .companyAddress("BJIT Baridhara Office 02, House No C, 7 Road No. 2, Dhaka")
                    .authorityName(admitCardInfoDto.getAuthorityName())
                    .authoritySignatureImage(signature)
                    .instructions(admitCardInfoDto.getInstructions())
                    .location(admitCardInfoDto.getLocation())
                    .examDate(admitCardInfoDto.getExamDate())
                    .time(admitCardInfoDto.getTime())
                    .build();
            AdmitCardInformation saved = admitCardInformationRepository.save(admitCardInformation);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<?> getAdmitId(Principal principal,Long circularId) {
        User user = userService.getUserByPrincipal(principal).orElseThrow();
        ApplicantProfile applicantProfile = applicantProfileRepository.findByUserId(user.getId()).orElseThrow();
        Application application=applicationRepository.findByCircularIdAndApplicantId(circularId,applicantProfile.getId()).orElseThrow();
        Resource admit = application.getAdmit();
        if(admit==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ApplicationInfoDto.builder()
                        .currentRoundAdmitId(admit.getId()).build()) ;
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
