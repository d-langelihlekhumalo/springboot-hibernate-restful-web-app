package com.andile.basicblog.service.Impl;

import com.andile.basicblog.entity.User;
import com.andile.basicblog.entity.Role;
import com.andile.basicblog.exception.APIException;
import com.andile.basicblog.payload.RegisterDTO;
import com.andile.basicblog.repository.RoleRepository;
import com.andile.basicblog.repository.UserRepository;
import com.andile.basicblog.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                                     RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST,
                    String.format("Email: %s already exist! Please try another option", user.getEmail()));
        }

        User objUser = new User();
        objUser.setName(user.getName());
        objUser.setEmail(user.getEmail());
        objUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        objUser.setRoles(roles);

        return userRepository.save(objUser);
    }

}
