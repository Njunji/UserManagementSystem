package com.company.usermanagement.event.listener;

import com.company.usermanagement.event.RegistrationCompleteEvent;
import com.company.usermanagement.userProfile.UserProfile;
import com.company.usermanagement.userProfile.userProfileService.UserProfileServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author Samwel Njunji
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserProfileServiceImpl userProfileService;
    private final JavaMailSender mailSender;
    private UserProfile theUserProfile;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //getnewuser
        theUserProfile = event.getUserProfile();
        //create verificationtoken for user
        String verificationToken = UUID.randomUUID().toString();
        //save verification token for user table in db
        userProfileService.saveUserProfileVerificationToken(theUserProfile, verificationToken);
        //build the verification url to be sent to user
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        //send the email
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click link to verify your UserProfile registration :  {}", url);

    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ theUserProfile.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("samwelnjunji@gmail.com", senderName);
        messageHelper.setTo(theUserProfile.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
