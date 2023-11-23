package com.andile.basicblog.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "User Model Information"
)

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "User name")
    @NotEmpty(message = "User's name must not be empty. Please provide a name")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "User email")
    @NotEmpty(message = "User's email must not be empty. Please provide a valid email address")
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Schema(description = "User password")
    @NotEmpty(message = "User's password must not be empty. Please provide a password")
    @Size(min = 4, message = "Password should at least be 4 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(description = "User account Creation Date")
    @CreationTimestamp
    private Instant date_created;

    @Schema(description = "User Role and Permissions")
    // Only allow unique roles
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @Schema(description = "Comments made by User")
    // Only contain unique comments
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
