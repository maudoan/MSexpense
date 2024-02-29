package com.mysunshine.ms.jpa.endpoints;

import com.mysunshine.ms.jpa.CrudEndpoint;
import com.mysunshine.ms.jpa.models.User;
import com.mysunshine.ms.jpa.services.UserService;
import com.mysunshine.ms.payloads.request.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserEndpoint extends CrudEndpoint<User, Long> {

    private UserService userService;

    public UserEndpoint(UserService service) {
        super(service);
        this.userService = service;
        this.baseUrl = "/api/users";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return  userService.register(signUpRequest);
    }
}
