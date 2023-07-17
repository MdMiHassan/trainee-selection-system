package com.example.tss.service.impl;

import com.example.tss.admit.AdmitCardMoldFactory;
import com.example.tss.constants.ResourceType;
import com.example.tss.entity.*;
import com.example.tss.repository.*;
import com.example.tss.service.AdmitCardService;
import com.example.tss.service.ResourceService;
import com.example.tss.util.CodeGenerator;
import com.google.zxing.WriterException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmitCardServiceImpl implements AdmitCardService {
    private final ResourceService resourceService;
    private final ResourceRepository resourceRepository;
    private final ApplicantProfileRepository applicantProfileRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final AdmitCardMoldFactory admitCardMoldFactory;
    private final CircularRepository circularRepository;
    private final ScreeningRoundRepository screeningRoundRepository;
    private final AdmitCardInformationRepository admitCardInformationRepository;
    private final CodeGenerator codeGenerator;

    @Override
    public ResponseEntity<?> retrieveAdmit(Long id) {
        return resourceService.getByIdAndResourceType(id, ResourceType.ADMITCARD);
    }

    @Override
    public ResponseEntity<?> downloadAdmit(Long circularId, Long roundId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        ApplicantProfile applicantProfile = applicantProfileRepository.findByUserId(user.getId()).orElseThrow();
        Circular circular = circularRepository.findById(circularId).orElseThrow();
        ScreeningRound screeningRound = screeningRoundRepository.findById(roundId).orElseThrow();
        Application application = applicationRepository.findByCircularIdAndCurrentRoundIdAndApplicantId(circularId, roundId, applicantProfile.getId()).orElseThrow();
        Resource admit = application.getAdmit();
        if (admit == null) {
            admit = resourceRepository.save(Resource.builder().build());
            AdmitCardInformation admitCardInformation = admitCardInformationRepository.findByCircularId(circular.getId()).orElseThrow();
            byte[] companyLogoLeft = admitCardInformation.getCompanyLogoLeft().getFileData();
            byte[] companyLogoRight = admitCardInformation.getCompanyLogoRight().getFileData();
            byte[] applicantPhoto = application.getProfileImage().getFileData();
            byte[] applicantSignature = application.getApplicantSignature().getFileData();
            byte[] authoritySignature = admitCardInformation.getAuthoritySignatureImage().getFileData();
            byte[] qrCode;
            try {
                ByteArrayOutputStream bios = new ByteArrayOutputStream();
                BufferedImage qrCodeImage = codeGenerator.generateQRCodeImage("/admits/verify/" + admit.getId(), 300, 300);
                ImageIO.write(qrCodeImage, "png", bios);
                qrCode = bios.toByteArray();
            } catch (WriterException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] barCode;
            try {
                ByteArrayOutputStream bios = new ByteArrayOutputStream();
                BufferedImage barCodeImage = codeGenerator.generateBarcodeImage(application.getId().toString(), 300, 100);
                ImageIO.write(barCodeImage, "png", bios);
                barCode = bios.toByteArray();
            } catch (WriterException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

            String[] splitInstruction = admitCardInformation.getInstructions().split("\n");
            List<String> instructions = Arrays.asList(splitInstruction);
            try {

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
                        .forgeAdmitCard()
                        .getAdmitHTML();

                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withHtmlContent(admitCardHtml, null);
                ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
                builder.toStream(pdfOutputStream);
                builder.run();
                admit.setFileData(pdfOutputStream.toByteArray());
                admit.setOwner(user);
                admit.setFileFormat("pdf");
                admit.setDeletedByUser(false);
                admit.setFileDelete(false);
                admit.setFileName(admit.getId() + ".pdf");
                admit.setFileRead(false);
                admit.setFileRead(true);
                admit.setUploadAt(new Timestamp(System.currentTimeMillis()));
                resourceRepository.save(admit);
                System.out.println("admit created successfully.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resourceService.getByIdAndResourceTypeAndOwnerId(admit.getId(),ResourceType.ADMITCARD,user.getId());
    }
}
