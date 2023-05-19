package com.company.usermanagement.userProfile;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmail(String email);
}
