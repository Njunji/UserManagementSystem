package com.company.usermanagement.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository <VerificationToken, Long>{
    VerificationToken findByToken(String token);
}
