package com.andile.basicblog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RegisterDTO {
    private Long id;

    @NotEmpty(message = "User's name must not be empty. Please provide a name")
    private String name;

    @NotEmpty(message = "User's email must not be empty. Please provide a valid email address")
    @Email
    private String email;

    @NotEmpty(message = "User's password must not be empty. Please provide a password")
    @Size(min = 4, message = "Password should at least be 4 characters long")
    private String password;
}
