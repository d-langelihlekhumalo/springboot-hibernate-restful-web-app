package com.andile.basicblog.controller;

import com.andile.basicblog.entity.Post;
import com.andile.basicblog.entity.User;
import com.andile.basicblog.payload.UserDTO;
import com.andile.basicblog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "READ | UPDATE | DELETE REST APIs for User Resource"
)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "GET all Users REST API",
            description = "GET all Users REST API is used to get users"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @Operation(
            summary = "GET User by id REST API",
            description = "GET User by id REST API is used to get a user by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public UserDTO findUserById(@PathVariable(name="userId") long id) {
        return userService.findById(id);
    }

    @Operation(
            summary = "UPDATE User by id REST API",
            description = "UPDATE User by id REST API is used to get a update by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody User user, Principal principal) {
        // @PathVariable(name="userId") long id,
        UserDTO objUpdateUser = userService.update(user, principal);
        return new ResponseEntity<>(objUpdateUser, HttpStatus.OK);
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "DELETE User by id REST API",
            description = "DELETE User by id REST API is used to delete a user by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable(name="userId") long id, Principal principal) {
        userService.deleteById(id, principal);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }
}
