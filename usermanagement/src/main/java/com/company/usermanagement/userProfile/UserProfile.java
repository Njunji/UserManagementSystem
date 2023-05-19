package com.company.usermanagement.userProfile;

import com.company.usermanagement.enumeration.Status;
import com.company.usermanagement.enumeration.UserProfileRole;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile  {

    @Id
    @SequenceGenerator(name= "companyz_sequence", sequenceName = "companyz_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "companyz_sequence")
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    private LocalDateTime localDateTime;
    private String maritalStatus;
    private String nationality;
    private String username;
    @NaturalId(mutable = true) //no two users can use same email
    private String email;
    private String password;
    private boolean isEnabled=false;
    private Status status;
    private boolean locked;
    private String userProfileRole;
    private boolean mfaEnabled;
    private String secret;

}
