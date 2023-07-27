package com.example.tss.constants.molds;

public interface HTMLMold {
    String ADMIT_CARD = """
            <html>
                        
            <head>
                <style>
                    * {
                        border: 0;
                        margin: 0;
                        box-sizing: border-box;
                    }
                        
                    img {
                        display: block;
                    }
                        
                    .instructions ol li p {
                        font-size: 14px;
                    }
                        
                    .basic-info div p,
                    .basic-info div h4 {
                        font-size: 16px;
                    }
                        
                    .heading {
                        display: table;
                        width: 100%;
                    }
                        
                    .company-logo-left,
                    .companyIntro,
                    .company-logo-right {
                        display: table-cell;
                        vertical-align: middle;
                    }
                        
                    .company-logo-right,
                    .company-logo-left {
                        width: 80px;
                    }
                        
                    .company-logo-left img,
                    .company-logo-right img {
                        margin: 0 auto;
                        width: 70px;
                    }
                        
                    .companyIntro {
                        text-align: center;
                        
                    }
                        
                    .heading {
                        padding-bottom: 5px;
                        border-bottom: .5px solid #000000;
                    }
                        
                    .companyIntro h1 {
                        margin-bottom: 5px;
                        font-size: 36px;
                        color: #383691;
                    }
                        
                        
                    .agenda {
                        text-align: center;
                        margin: 20px 0 30px 0;
                    }
                        
                    .agenda h2 {
                        font-size: 24px;
                    }
                        
                    .companyIntro p {
                        font-style: italic;
                        font-size: 12px;
                    }
                        
                    .applicants-data {
                        width: 100%;
                        margin-bottom: 20px;
                    }
                        
                    .security-data {
                        width: 100px;
                    }
                        
                    .basic-info,
                    .security-data {
                        vertical-align: top;
                    }
                        
                    .basic-info {
                        display: table;
                    }
                        
                    .basic-info div {
                        display: table-row;
                    }
                        
                    .basic-info div h4 {
                        width: 150px;
                        display: table-cell;
                    }
                        
                    .applicants-sig {
                        width: 100px;
                    }
                        
                    .bar-code,
                    .applicants-sig img,
                    .applicants-photo,
                    .qr-code {
                        margin: 0 auto;
                    }
                        
                    .bar-code {
                        width: 100px;
                        height: 50px;
                    }
                        
                    .applicants-sig img {
                        width: 100px;
                        height: 40px;
                    }
                        
                    .applicants-photo {
                        width: 100px;
                        height: 100px;
                    }
                        
                    .qr-code {
                        width: 100px;
                        height: 100px;
                    }
                        
                    .basic-info h3,
                    .instructions h3 {
                        margin-bottom: 5px;
                    }
                        
                    .instructions h3 {
                        width: 100%;
                        text-align: center;
                        margin-bottom: 10px;
                    }
                        
                    .instructions {
                        margin-bottom: 8%;
                        border: .5px solid #000000;
                        padding: 10px 15px 15px 5px;
                    }
                        
                    .auth-inv-signature,
                    .auth-inv-signature tr {
                        width: 100%;
                    }
                        
                    .auth-inv-signature tr td {
                        width: 50%;
                        text-align: center;
                        vertical-align: top;
                    }
                        
                    .auth-sig img,
                    .auth-sig p,
                    .auth-sig h4,
                    .inv-sig h4 {
                        width: 200px;
                        margin: 0 auto;
                        text-align: center;
                    }
                        
                    .auth-sig img {
                        width: 200px;
                        height: 70px;
                    }
                        
                    .applicants-sig h4,
                    .inv-sig h4,
                    .auth-sig h4 {
                        border-top: .5px solid #000000;
                    }
                        
                    .applicants-sig h4 {
                        width: 100%;
                        text-align: center;
                    }
                        
                    .applicants-sig {
                        width: 100%;
                        margin: 10px 0 30px 0;
                    }
                </style>
            </head>
                        
            <body>
                <div class="heading">
                    <div class="company-logo-left">
                        <img id="companyLogoLeft" alt="logo" ></img>
                    </div>
                    <div class="companyIntro">
                        <h1 id="companyName"></h1>
                        <p id="companyAddress"></p>
                    </div>
                    <div class="company-logo-right">
                        <img id="companyLogoRight" alt="logo" ></img>
                    </div>
                </div>
                <div class="agenda">
                    <h2>ADMIT CARD - <span id="examName"></span></h2>
                </div>
                <table class="applicants-data">
                    <tr>
                        <td class="basic-info">
                            <h3>Basic Information</h3>
                            <div id="basicInformation"></div>
                        </td>
                        <td class="security-data">
                            
                            <img id="applicantPhoto" alt="applicants" class="applicants-photo" ></img>
                            <div class="applicants-sig">
                                
                                <h4 style="margin-top:50px;">Signature</h4>
                            </div>
                            <img id="qrCode" alt="qrcode" class="qr-code" ></img>
                        </td>
                    </tr>
                </table>
                <div class="instructions">
                    <h3>Instructions</h3>
                    <ol id="instructions"></ol>
                </div>
                <table class="auth-inv-signature">
                    <tr>
                        <td class="auth-sig"><img id="authoritySignature" alt="authority signature" ></img></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="auth-sig">
                            <h4 id="authorityName"></h4>
                            <p id="authorityDesignation"></p>
                        </td>
                        <td class="inv-sig">
                            <h4>Invigilator Signature</h4>
                        </td>
                    </tr>
                </table>
            </body>
                        
            </html>
            """;
    String REJECTION_EMAIL = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title></title>
                <style>
                    body{
                        font-family: 'Helvetica', 'Arial', sans-serif;
                        color: #000000E0;
                    }
                </style>
            </head>
            <body>
            <p>Dear <span id="applicant-name">[Candidate's Name]</span>,</p>
            <p>
                We hope this email finds you well. We wanted to take a moment to provide you with an update regarding your recent
                application for the job position <span id="job-position">[Job Position]</span>
                </strong> at BJIT Limited. After careful consideration and evaluation, we regret to inform you that you have not
                been selected to proceed to the next round of the recruitment process.We appreciate your understanding and wish you
                every success in your future endeavors.
            </p>
            <p>
                Thank you once again for considering BJIT Limited as an opportunity for your professional growth. We value your
                interest and wish you all the best in your career.
            </p>
            <span>Best regards,</span><br>
            <span id="regards-name">[Your Name]</span><br>
            <span id="regards-position">[Your Position]</span><br>
            <span id="regards-companyname">[Company Name]</span><br>
            <span id="regards-information">[Contact Information]</span><br>
                        
            </body>
            </html>
            """;
    String SCREENING_INVITATION_EMAIL = """
            <!DOCTYPE html>
            <html lang="en">
                        
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title></title>
                <style>
                    body{
                        font-family: 'Helvetica', 'Arial', sans-serif;
                        color: #000000E0;
                    }
                </style>
            </head>
                        
            <body>
            <p>Dear <span id="applicant-name">[Candidate's Name]</span>,</p>
            <p><strong>Congratulations!</strong> We are delighted to inform you that <strong>
                you have been shortlisted for the next stage of the recruitment process for the position of <span id="job-position">[Job Position]</span>
            </strong> at BJIT Limited. Your qualifications and experience have impressed us, and we believe you would be an
                excellent fit for our team. We would like to invite you to participate in the upcoming round, where we will further
                evaluate your skills and suitability for the role and we believe you would be an excellent fit for our team.
            </p>
            <span>The details of the next round are as follows:</span><br>
            <strong>Round: </strong> <span id="round-name">[Round Name]</span><br>
            
            <p id="admit-instructions"></p>
            <p>Once again, congratulations on your achievement! We wish you all the best in this stage of the recruitment
                process.</p>
            <span>Best regards,</span><br>
            <span id="regards-name">From TSS</span><br>
            <span id="regards-position"></span><br>
            <span id="regards-company"></span><br>
            <span id="regards-information"></span><br>
            </body>
                        
            </html>
            """;
    String THANKS_FOR_APPLYING_EMAIL = """
            <!DOCTYPE html>
            <html lang="en">
                        
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title></title>
                <style>
                    body{
                        font-family: 'Helvetica', 'Arial', sans-serif;
                        color: #000000E0;
                    }
                </style>
            </head>
                        
            <body>
            <p>Dear <span id="applicant-name">[Candidate's Name]</span>,</p>
            <p>
                We would like to express our gratitude for taking the time to apply for the job position at <span id="job-position">[Job Position]</span>.
                We appreciate your interest in joining our team and would like to inform you that we have received your
                application. Our recruitment team will carefully review your application and assess your qualifications in relation to the
                job requirements. We aim to provide a fair and thorough evaluation process to ensure that all candidates are
                given equal consideration. Please note that due to the high volume of applications we receive, it may take some time for us to complete the
                initial screening process. We kindly ask for your patience during this period. Rest assured that we will notify
                you of any updates regarding your application status. If your application does not progress to the next stage, we will notify you accordingly. Nonetheless, we
                encourage you to explore other opportunities within our organization in the future.
            </p>
            <p>
                Once again, thank you for choosing to apply with us. We value your interest in becoming a part of our team and
                wish you the best of luck with your application. If you have any questions or require further information, please feel free to reach out us at
                <span id="contact-details">[Recruitment Team Contact Details]</span>
            </p>
            <span>Best regards,</span><br>
            <span id="regards-name">[Your Name]</span><br>
            <span id="regards-position">[Your Position]</span><br>
            <span id="regards-company">[Company Name]</span><br>
            <span id="regards-information">[Contact Information]</span><br>
            </body>
                        
            </html>
            """;
}
