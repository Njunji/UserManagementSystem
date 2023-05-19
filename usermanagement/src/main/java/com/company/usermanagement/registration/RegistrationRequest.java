package com.company.usermanagement.registration;

public record RegistrationRequest
    ( String firstName,
      String lastName,
      String email,
      String password,
      String userProfileRole
    ) {
}
