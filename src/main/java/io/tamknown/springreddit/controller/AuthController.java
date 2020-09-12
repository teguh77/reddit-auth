package io.tamknown.springreddit.controller;

import io.tamknown.springreddit.dto.LoginDTO;
import io.tamknown.springreddit.dto.RefreshRequest;
import io.tamknown.springreddit.dto.RegisterRequest;
import io.tamknown.springreddit.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

// TODO: send email verification after register user

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login,
                                   HttpServletResponse response) {
        return authService.login(login, response);
    }

    @PostMapping("generate")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshRequest refreshRequest, HttpServletResponse response) {
        return authService.refresh(refreshRequest, response);
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshRequest refreshRequest, HttpServletResponse response) {
        authService.logout(refreshRequest, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logout successfully");
    }

}
