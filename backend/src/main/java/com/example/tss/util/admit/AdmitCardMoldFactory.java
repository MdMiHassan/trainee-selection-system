package com.example.tss.util.admit;

import com.example.tss.constants.molds.HTMLMold;
import com.example.tss.util.ImageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AdmitCardMoldFactory {
    public AdmitCardMold getAdmitCardMold() {
        Document document = Jsoup.parse(HTMLMold.ADMIT_CARD);
        document.outputSettings().syntax( Document.OutputSettings.Syntax.xml);
        return new SimpleAdmitCardMold(document);
    }

    private static class SimpleAdmitCardMold implements AdmitCardMold {
        private final Document admit;
        private String companyName;
        private String companyAddress;
        private String examName;
        private String authorityName;
        private String authorityDesignation;
        private byte[] barCode;
        private byte[] qrCode;
        private byte[] applicantPhoto;
        private byte[] companyLogoLeft;
        private byte[] companyLogoRight;
        private byte[] applicantSignature;
        private byte[] authoritySignature;
        private List<String> instructions;
        private LinkedHashMap<String, String> basicInfo;

        public SimpleAdmitCardMold(Document admit) {
            this.admit = admit;
        }


        public SimpleAdmitCardMold examName(String examName) {
            this.examName = examName;
            return this;
        }

        public SimpleAdmitCardMold companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public SimpleAdmitCardMold companyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
            return this;
        }

        public SimpleAdmitCardMold authorityName(String authorityName) {
            this.authorityName = authorityName;
            return this;
        }

        public SimpleAdmitCardMold authorityDesignation(String authorityDesignation) {
            this.authorityDesignation = authorityDesignation;
            return this;
        }

        public SimpleAdmitCardMold barCode(byte[] barCode) {
            this.barCode = barCode;
            return this;
        }

        public SimpleAdmitCardMold qrCode(byte[] qrCode) {
            this.qrCode = qrCode;
            return this;
        }

        public SimpleAdmitCardMold applicantPhoto(byte[] applicantPhoto) {
            this.applicantPhoto = applicantPhoto;
            return this;
        }

        public SimpleAdmitCardMold companyLogoLeft(byte[] companyLogoLeft) {
            this.companyLogoLeft = companyLogoLeft;
            return this;
        }

        public SimpleAdmitCardMold companyLogoRight(byte[] companyLogoRight) {
            this.companyLogoRight = companyLogoRight;
            return this;
        }

        public SimpleAdmitCardMold applicantSignature(byte[] applicantSignature) {
            this.applicantSignature = applicantSignature;
            return this;
        }

        public SimpleAdmitCardMold authoritySignature(byte[] authoritySignature) {
            this.authoritySignature = authoritySignature;
            return this;
        }

        public SimpleAdmitCardMold instructions(List<String> instructions) {
            this.instructions = instructions;
            return this;
        }

        public SimpleAdmitCardMold basicInfo(LinkedHashMap<String, String> basicInfo) {
            this.basicInfo = basicInfo;
            return this;
        }

        public String getAdmitHTML() {
            return admit.html();
        }

        public SimpleAdmitCardMold forgeAdmitCard() throws Exception {
            long start = System.currentTimeMillis();
            if (companyName != null) {
                admit.getElementById("companyName").text(companyName);
            }
            if (companyAddress != null) {
                Objects.requireNonNull(admit.getElementById("companyAddress")).text(companyAddress);
            }
            if (basicInfo != null) {
                Element basicInformation = admit.getElementById("basicInformation");
                for (String infoTitle : basicInfo.keySet()) {
                    Element divElement = new Element(Tag.valueOf("div"), "");
                    basicInformation.appendChild(divElement);
                    Element h4Element = new Element(Tag.valueOf("h4"), "");
                    h4Element.text(infoTitle);
                    divElement.appendChild(h4Element);
                    Element pElement = new Element(Tag.valueOf("p"), "");
                    System.out.println(basicInfo.get(infoTitle));
                    String s = basicInfo.get(infoTitle);
                    if(s!=null){
                        pElement.text(basicInfo.get(infoTitle));
                        System.out.println("After p");
                        divElement.appendChild(pElement);
                    }
                }
            }
            if (instructions != null) {
                StringBuilder instructionChunk = new StringBuilder();
                Element instructionsElement = admit.getElementById("instructions");
                for (String instruction : instructions) {
                    Element liElement = new Element(Tag.valueOf("li"), "");
                    instructionsElement.appendChild(liElement);
                    Element pElement = new Element(Tag.valueOf("p"), "");
                    pElement.text(instruction);
                    liElement.appendChild(pElement);
                }
            }
            if (examName != null) {
                admit.getElementById("examName").text(examName);
            }
            if (authorityName != null) {
                admit.getElementById("authorityName").text(authorityName);
            }
            if (authorityDesignation != null) {
                admit.getElementById("authorityDesignation").text(authorityName);
            }
            if (companyLogoLeft != null) {
                admit.getElementById("companyLogoLeft").attr("src", ImageUtils.encodeImageToBase64(companyLogoLeft));
            }
            if (companyLogoRight != null) {
                admit.getElementById("companyLogoRight").attr("src", ImageUtils.encodeImageToBase64(companyLogoRight));
            }
//            if (barCode != null) {
//                admit.getElementById("barCode").attr("src", ImageUtils.encodeImageToBase64(barCode));
//            }
            if (qrCode != null) {
                admit.getElementById("qrCode").attr("src", ImageUtils.encodeImageToBase64(qrCode));
            }
            if (authoritySignature != null) {
                admit.getElementById("authoritySignature").attr("src", ImageUtils.encodeImageToBase64(authoritySignature));
            }
            if (applicantSignature != null) {
                admit.getElementById("applicantSignature").attr("src", ImageUtils.encodeImageToBase64(applicantSignature));
            }
            if (applicantPhoto != null) {
                admit.getElementById("applicantPhoto").attr("src", ImageUtils.encodeImageToBase64(applicantPhoto));
            }
            System.out.println("time to replace: " + (System.currentTimeMillis() - start));
            return this;
        }
    }
}
