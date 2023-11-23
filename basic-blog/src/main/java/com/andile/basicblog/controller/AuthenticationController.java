package com.andile.basicblog.controller;

import com.andile.basicblog.entity.User;
import com.andile.basicblog.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "CREATE User REST API Resource"
)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Create register rest endpoint || allow users to register
    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save a user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        return new ResponseEntity<>(authenticationService.register(user), HttpStatus.CREATED);
    }

}
