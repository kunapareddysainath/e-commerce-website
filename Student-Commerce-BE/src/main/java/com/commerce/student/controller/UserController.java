package com.commerce.student.controller;

import com.commerce.student.model.entity.Address;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.UpdateEmailAddressRequest;
import com.commerce.student.model.request.UpdatePhoneNumberRequest;
import com.commerce.student.model.request.UpdateUserRequest;
import com.commerce.student.model.response.JwtAuthenticationResponse;
import com.commerce.student.repositiory.AddressRepository;
import com.commerce.student.repositiory.UserRepository;
import com.commerce.student.security.JwtProvider;
import com.commerce.student.security.UserPrincipal;
import com.commerce.student.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider tokenProvider;

    @PostMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailAddressRequest updateEmailAddressRequest) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPrincipal == null) throw new NullPointerException("This user is not valid");

        User user = userRepository.findByUserId(userPrincipal.getId());
        if (user == null) throw new UsernameNotFoundException("No user is found");

        if (user.getEmailAddress().equals(updateEmailAddressRequest.getOldEmailAddress())) {
            user.setEmailAddress(updateEmailAddressRequest.getNewEmailAddress());
            userRepository.save(user);
            return ResponseEntity.ok("Email address is updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please check your email address");
        }

    }

    @PostMapping("/updatePhoneNumber")
    public ResponseEntity<?> updatePhoneNumber(@RequestBody UpdatePhoneNumberRequest updatePhoneNumberRequest) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPrincipal == null) throw new NullPointerException("This user is not valid");

        User user = userRepository.findByUserId(userPrincipal.getId());
        if (user == null) throw new UsernameNotFoundException("No user is found");

        if (user.getPhoneNumber().equals(updatePhoneNumberRequest.getOldPhoneNumber())) {
            user.setPhoneNumber(updatePhoneNumberRequest.getNewPhoneNumber());
            userRepository.save(user);
            return ResponseEntity.ok("Phone number is updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please check your phone number");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPrincipal == null) throw new NullPointerException("This user is not valid");

        User user = userRepository.findByUserId(userPrincipal.getId());
        if (user == null) throw new UsernameNotFoundException("No user is found");

        return ResponseEntity.ok(user);
    }


    @PostMapping("/updateUserInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByUserId(updateUserRequest.getUserId());
        Address dbAddress = addressRepository.findById(updateUserRequest.getAddressId()).orElse(null);

        if (user == null || dbAddress == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User or address not found to update");
        }

        boolean updateUser = userService.updateUserFields(user, updateUserRequest);
        boolean updateAddress = userService.updateAddressFields(dbAddress, updateUserRequest);

        if (updateUser) userRepository.save(user);
        if (updateAddress) addressRepository.save(dbAddress);

        try {
            String jwt = tokenProvider.refreshToken(user);

            Map<String, Object> objectMap = new HashMap<>();

            objectMap.put("accessToken", jwt);
            objectMap.put("user", user);
            objectMap.put("address", dbAddress);

            return ResponseEntity.ok(objectMap);

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Please provide valid username and password");
        }

    }


}
