package com.commerce.student.configuration;

import com.commerce.student.model.entity.Role;
import com.commerce.student.model.entity.User;
import com.commerce.student.repositiory.RoleRepository;
import com.commerce.student.repositiory.UserRepository;
import com.commerce.student.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the admin user exists
        if (userRepository.findByUsername("admin")==null) {

            Role role =  roleRepository.findByLabel("Admin");

            if (role==null) throw new NullPointerException("No role is define this label");

            // Create a new admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode(Constants.ADMIN_DEFAULT_PASSWORD));
            admin.setEmailAddress(Constants.ADMIN_EMAIL_ADDRESS);
            admin.setPhoneNumber(Constants.ADMIN_PHONE_NUMBER);
            admin.setRole(role);

            userRepository.save(admin);
        }
    }
}
