package com.company.usermanagement.userProfile.userProfileService;

import com.company.usermanagement.exception.UserAlreadyExistsException;
import com.company.usermanagement.registration.EmailValidator;
import com.company.usermanagement.registration.RegistrationRequest;
import com.company.usermanagement.registration.token.VerificationToken;
import com.company.usermanagement.registration.token.VerificationTokenRepository;
import com.company.usermanagement.userProfile.UserProfile;
import com.company.usermanagement.userProfile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public UserProfile create(UserProfile userProfile) {
        log.info("Saving new User:{}", userProfile.getFirstName(), userProfile.getLastName());
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile get(Long id) {
        log.info("Fetching userProfile by ID:", id);
        return userProfileRepository.findById(id).get();
    }

    @Override
    public UserProfile update(UserProfile userProfile) {
        log.info("Updating UserProfile:{}", userProfile.getFirstName(), userProfile.getLastName());
        return userProfileRepository.save(userProfile);
    }

    @Override
    public List<UserProfile> getUsers() {
        return null;
    }

    @Override
    public UserProfile registerUser(RegistrationRequest request) {
        Optional<UserProfile> userProfile = this.findByEmail(request.email());
        if (userProfile.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.email() + "already exists");
        }
        var newUserProfile = new UserProfile();
        newUserProfile.setFirstName(request.firstName());
        newUserProfile.setLastName(request.lastName());
        newUserProfile.setEmail(request.email());
        newUserProfile.setPassword(passwordEncoder.encode(request.password()));
        newUserProfile.setUserProfileRole(request.userProfileRole());
        return null;
    }

    @Override
    public Optional<UserProfile> findByEmail(String email) {
        return userProfileRepository.findByEmail(email);
    }

    @Override
    public void saveUserProfileVerificationToken(UserProfile theUserProfile, String token) {
        var verificationToken = new VerificationToken(token, theUserProfile);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if (token == null) {
            return "Invalid verification token";
        }
        UserProfile userProfile = token.getUserProfile();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            tokenRepository.delete(token);
            return "Token already expired";
        }

        userProfile.setEnabled(true);
        userProfileRepository.save(userProfile);
        return "valid";
    }
}