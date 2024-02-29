package org.example.medicalcontrol.auth.services;


import org.example.medicalcontrol.security.TokenService;
import org.example.medicalcontrol.users.dtos.AuthenticationDto;
import org.example.medicalcontrol.users.dtos.LoginResponseDto;
import org.example.medicalcontrol.users.dtos.RegisterDto;
import org.example.medicalcontrol.users.models.UsersModel;
import org.example.medicalcontrol.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Validated AuthenticationDto data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UsersModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        if(this.usersRepository.findByEmail(registerDto.email()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        UsersModel newUser = new UsersModel(registerDto.name(), registerDto.email(), encryptedPassword, registerDto.role());
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        this.usersRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
