package com.mysunshine.ms.jpa.endpoints;

import com.mysunshine.ms.constant.constants.Constants;
import com.mysunshine.ms.jpa.CrudEndpoint;
import com.mysunshine.ms.jpa.models.User;
import com.mysunshine.ms.jpa.services.UserService;
import com.mysunshine.ms.payloads.request.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserEndpoint extends CrudEndpoint<User, Long> {

    private UserService userService;

    public UserEndpoint(UserService service) {
        super(service);
        this.userService = service;
        this.baseUrl = "/api/users";
    }
    @GetMapping(path = "/current")
    public User currentUser(HttpServletRequest request) {
        String authorization = request.getHeader(Constants.AUTH_HEADER_STRING);
        if(authorization != null && authorization.startsWith(Constants.AUTH_TOKEN_PREFIX)) {
            String token = authorization.substring(Constants.AUTH_TOKEN_PREFIX.length());
            if (token.isEmpty()) return null;
            return userService.currentUser(token);
        }else {
            return null;
        }
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public User get(@PathVariable(value = "id") Long aLong) {
        return super.get(aLong);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return  userService.register(signUpRequest);
    }
}
