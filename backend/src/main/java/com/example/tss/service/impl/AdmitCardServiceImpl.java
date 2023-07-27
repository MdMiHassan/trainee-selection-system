package com.example.tss.service.impl;

import com.example.tss.constants.ResourceType;
import com.example.tss.dto.AdmitCardInfoDto;
import com.example.tss.dto.ResourceDto;
import com.example.tss.entity.*;
import com.example.tss.exception.AdmitCardGenerationException;
import com.example.tss.exception.ResourceNotFoundException;
import com.example.tss.repository.AdmitCardInformationRepository;
import com.example.tss.repository.ApplicationRepository;
import com.example.tss.repository.CircularRepository;
import com.example.tss.repository.ResourceRepository;
import com.example.tss.service.AdmitCardService;
import com.example.tss.util.CodeGenerator;
import com.example.tss.util.SystemUtils;
import com.example.tss.util.admit.AdmitCardMoldFactory;
import com.google.zxing.WriterException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

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
    private final ModelMapper modelMapper;

    @Override
    public ResourceDto retrieveAdmit(Long resourceId) {
        Resource resource = resourceRepository.findByIdAndResourceType(resourceId, ResourceType.ADMITCARD)
                .orElseThrow(() -> new ResourceNotFoundException(resourceId.toString()));
        return modelMapper.map(resource, ResourceDto.class);
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
            String authorityName = admitCardInformation.getAuthorityName();
            String examLocation = admitCardInformation.getLocation();

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
            System.out.println(e.getMessage());
            throw new AdmitCardGenerationException();
        }
    }

    @Override
    @Transactional
    public AdmitCardInfoDto saveAdmitInfo(Long circularId, AdmitCardInfoDto admitCardInfoDto) {
        try {
            Resource logoleftResource = createLogoResource("classpath:static/logo/bjitacademylogo.png");
            Resource logoRightResource = createLogoResource("classpath:static/logo/bjitlogo.png");
            Resource signature = resourceRepository.findById(admitCardInfoDto.getAuthoritySignatureImageId()).orElseThrow();
            Circular circular = circularRepository.findById(circularId).orElseThrow();
            Resource savedLogoleftResource = resourceRepository.save(logoleftResource);
            Resource savedLogoRightResource = resourceRepository.save(logoRightResource);
            AdmitCardInformation admitCardInformation = AdmitCardInformation.builder()
                    .circular(circular)
                    .companyLogoLeft(savedLogoleftResource)
                    .companyLogoRight(savedLogoRightResource)
                    .companyName("BJIT ACADEMY")
                    .companyAddress("BJIT Baridhara Office 02, House No C, 7 Road No. 2, Dhaka")
                    .authorityName(admitCardInfoDto.getAuthorityName())
                    .authoritySignatureImage(signature)
                    .instructions(admitCardInfoDto.getInstructions())
                    .location(admitCardInfoDto.getLocation())
                    .examDate(admitCardInfoDto.getExamDate())
                    .time(admitCardInfoDto.getTime())
                    .build();
            AdmitCardInformation savedAdmitCardInformation = admitCardInformationRepository.save(admitCardInformation);
            return modelMapper.map(savedAdmitCardInformation, AdmitCardInfoDto.class);
        } catch (IOException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

    private Resource createLogoResource(String path) throws IOException {
        byte[] logoContent = resourceLoader.getResource(path).getInputStream().readAllBytes();
        return Resource.builder().fileData(logoContent).build();
    }

    private byte[] convertHtmlToPdf(String html) throws IOException {
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        builder.toStream(pdfOutputStream);
        builder.run();
        return pdfOutputStream.toByteArray();
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
