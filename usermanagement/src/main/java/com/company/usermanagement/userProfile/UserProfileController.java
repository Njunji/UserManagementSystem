package com.company.usermanagement.userProfile;

import com.company.usermanagement.models.Response;
import com.company.usermanagement.userProfile.userProfileService.UserProfileServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/userProfile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileServiceImpl userProfileService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid UserProfile userProfile){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("userProfile", userProfileService.create(userProfile)))
                        .message("UserProfile Created")
                        .status(CREATED)
                        .statusCode(CREATED.value ())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getUserProfile(@PathVariable("id ") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("userProfile", userProfileService.get(id)))
                        .message("UserProfile Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
    @GetMapping("/get")
    public List<UserProfile> getUsers(){
        return userProfileService.getUsers();
    }


}
