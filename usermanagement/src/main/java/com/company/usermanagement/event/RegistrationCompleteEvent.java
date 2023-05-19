package com.company.usermanagement.event;

import com.company.usermanagement.userProfile.UserProfile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private UserProfile userProfile;
    private String applicationUrl;

    public RegistrationCompleteEvent(UserProfile userProfile,String applicationUrl) {
        super(userProfile);
    }
}
