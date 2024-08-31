package com.commerce.student.controller;

import com.commerce.student.model.entity.Role;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.CreateUserRequest;
import com.commerce.student.model.request.LoginRequest;
import com.commerce.student.model.response.JwtAuthenticationResponse;
import com.commerce.student.repositiory.RoleRepository;
import com.commerce.student.repositiory.UserRepository;
import com.commerce.student.security.JwtProvider;
import com.commerce.student.security.UserPrincipal;
import com.commerce.student.utility.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            if (request.getName() == null || request.getUsername() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body("Name, username, or password is missing.");
            }

            User existingUser = userRepository.findByUsername(request.getUsername());

            if (existingUser != null) {
                return ResponseEntity.badRequest().body("Username already exists. Please choose a different one.");
            }

            Role role = roleRepository.findByLabel(Constants.USER);
            if (role == null) {
                return ResponseEntity.badRequest().body("Role doesn't exists. Please choose a correct role.");
            }
            userRepository.save(new User(request.getName(), request.getUsername(), request.getPhoneNumber(), request.getEmailAddress(), passwordEncoder.encode(request.getPassword()), role));
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user due to an internal server error.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        User existingUser = userRepository.findByUsername(request.getUsername());

        if (existingUser == null) {
            throw new UsernameNotFoundException("Please provide valid username and password");
        }

   /*     if (!existingUser.getRole().getLabel().equals(Constants.USER)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an user to login to this end point");
        }*/

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String jwt = tokenProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userPrincipal));

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Please provide valid username and password");
        }
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@Valid @RequestBody LoginRequest request) {

        User existingUser = userRepository.findByUsername(request.getUsername());


        if (existingUser == null) {
            throw new UsernameNotFoundException("Please provide valid username and password");
        }

        if (!existingUser.getRole().getLabel().equals(Constants.ADMIN)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an admin to login to this end point");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String jwt = tokenProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userPrincipal));

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Please provide valid username and password");
        }
    }

}
