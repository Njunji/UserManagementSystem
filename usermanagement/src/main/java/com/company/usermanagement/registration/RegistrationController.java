package com.company.usermanagement.registration;

import com.company.usermanagement.event.RegistrationCompleteEvent;
import com.company.usermanagement.registration.token.VerificationToken;
import com.company.usermanagement.registration.token.VerificationTokenRepository;
import com.company.usermanagement.userProfile.UserProfile;
import com.company.usermanagement.userProfile.userProfileService.UserProfileServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserProfileServiceImpl userProfileService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request, final HttpServletRequest httpServletRequest){
        UserProfile userProfile = userProfileService.registerUser(request);
        publisher.publishEvent(new RegistrationCompleteEvent(userProfile, applicationUrl(httpServletRequest)));
        return "Success!  Please, check your email for verification link";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUserProfile().isEnabled()){
            return "This account has already been verified,Kindly proceed to login.";
        }
        String verificationResult = userProfileService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Login to your account";
        }
        return "Invalid verification token";
    }
    public String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
    }
}
