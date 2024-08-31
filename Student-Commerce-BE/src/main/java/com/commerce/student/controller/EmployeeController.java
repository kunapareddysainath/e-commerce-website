package com.commerce.student.controller;

import com.commerce.student.model.entity.Role;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.CreateEmployeeRequest;
import com.commerce.student.repositiory.RoleRepository;
import com.commerce.student.repositiory.UserRepository;
import com.commerce.student.security.UserPrincipal;
import com.commerce.student.utility.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody @Valid CreateEmployeeRequest request) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //checking the role of user whether authorized to register an employee or not! Only Admin or Manager as the authority to hire an employee
        if (userPrincipal != null && !(userPrincipal.getRole().getLabel().equalsIgnoreCase(Constants.ADMIN) || userPrincipal.getRole().getLabel().equalsIgnoreCase(Constants.MANAGER))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not authorized to hire an employee");
        }

        try {
            if (request.getName() == null || request.getUsername() == null) {
                return ResponseEntity.badRequest().body("Name, username, or password is missing.");
            }

            User existingUser = userRepository.findByUsername(request.getUsername());

            if (existingUser != null) {
                return ResponseEntity.badRequest().body("Username already exists. Please choose a different one.");
            }

            Role role = roleRepository.findByLabel(request.getRole());
            if (role == null) {
                return ResponseEntity.badRequest().body("Role doesn't exists. Please choose a correct role.");
            }
            userRepository.save(new User(request.getName(), request.getUsername(), request.getPhoneNumber(), request.getEmailAddress(), passwordEncoder.encode(Constants.DEFAULT_EMPLOYEE_PASSWORD), role));
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register employee due to an internal server error.");
        }
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //checking the role of user whether authorized to register an employee or not! Only Admin or Manager as the authority to hire an employee
        if (userPrincipal != null && !(userPrincipal.getRole().getLabel().equalsIgnoreCase(Constants.ADMIN) || userPrincipal.getRole().getLabel().equalsIgnoreCase(Constants.MANAGER))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not authorized to hire an employee");
        }

        Role role = roleRepository.findByLabel(Constants.USER);
        if (role == null) {
            return ResponseEntity.badRequest().body("Role doesn't exists. Please choose a correct role.");
        }

        List<User> employees = userRepository.findUsersWithRoleNotUser(role.getId());
        if (employees == null) {
            return ResponseEntity.badRequest().body("No employee is hire");
        }
        return ResponseEntity.ok(employees);
    }

}
