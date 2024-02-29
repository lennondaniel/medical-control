package org.example.medicalcontrol.auth.controllers;

import org.example.medicalcontrol.auth.services.AuthorizationService;
import org.example.medicalcontrol.users.dtos.AuthenticationDto;
import org.example.medicalcontrol.users.dtos.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated AuthenticationDto authenticationDto) {
        return this.authorizationService.login(authenticationDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        return this.authorizationService.register(registerDto);
    }
}
