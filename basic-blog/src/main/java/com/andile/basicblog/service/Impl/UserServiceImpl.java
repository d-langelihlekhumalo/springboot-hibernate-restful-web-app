package com.andile.basicblog.service.Impl;

import com.andile.basicblog.entity.Role;
import com.andile.basicblog.entity.User;
import com.andile.basicblog.exception.APIException;
import com.andile.basicblog.exception.ResourceExistsException;
import com.andile.basicblog.exception.ResourceNotFoundException;
import com.andile.basicblog.payload.UserDTO;
import com.andile.basicblog.payload.UserDTO;
import com.andile.basicblog.repository.RoleRepository;
import com.andile.basicblog.repository.UserRepository;
import com.andile.basicblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapToDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(long id) {
        Optional<User> result = userRepository.findById(id);
        User user = null;
        if (result.isPresent()) {
            user = result.get();
        } else {
            throw new ResourceNotFoundException("User", "id", String.valueOf(id));
        }
        return mapToDTO(user);
    }

    @Override
    public UserDTO update(User user, Principal principal) {
        // Get current user objects and authenticated user
        User objUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", user.getEmail()));

        User authenticatedUser = userRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", principal.getName()));

        // Check if user is updating his/her own record
        if (objUser.getId() != authenticatedUser.getId()) {
            throw new ResourceNotFoundException("User", "email", user.getEmail());
        }

        User objUpdatedUser = new User();
        objUpdatedUser.setId(objUser.getId());
        objUpdatedUser.setName(user.getName());
        objUpdatedUser.setEmail(user.getEmail());
        objUpdatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        objUpdatedUser.setDate_created(objUser.getDate_created());

        return mapToDTO(userRepository.save(objUser));
    }

    @Override
    public void deleteById(long id, Principal principal) {

        // Get current user objects and authenticated user
        User objUser = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", String.valueOf(id)));

        User authenticatedUser = userRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", principal.getName()));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = String.valueOf(auth.getAuthorities());

        if (!(role.equalsIgnoreCase("[ROLE_ADMIN]"))) {
            // Check if user is deleting his/her own record
            if (objUser.getId() != authenticatedUser.getId()) {
                throw new ResourceNotFoundException("User", "id", String.valueOf(id));
            }
        }

        // Disallow admin account from being deleted
        if (role.equalsIgnoreCase("[ROLE_ADMIN]")) {
            if (objUser.getEmail() == authenticatedUser.getEmail()) {
                throw new ResourceNotFoundException("User", "id", String.valueOf(id));
            }
        }

        // Delete the user's account
        userRepository.delete(objUser);
    }

    // Convert entity to DTO
    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword("Password Encrypted!");
        return userDTO;
    }

    // Convert DTO to entity
    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
        return user;
    }
}
