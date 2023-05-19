package com.company.usermanagement.userProfile.userProfileService;

import com.company.usermanagement.registration.RegistrationRequest;
import com.company.usermanagement.userProfile.UserProfile;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


public interface UserProfileService  {
    UserProfile create(UserProfile userProfile);
    UserProfile get(Long id);
    UserProfile update(UserProfile userProfile);

    List<UserProfile> getUsers();
    UserProfile registerUser(RegistrationRequest request);
    Optional<UserProfile> findByEmail(String email);

    void saveUserProfileVerificationToken(UserProfile theUserProfile, String verificationToken);

    String validateToken(String token);
}
