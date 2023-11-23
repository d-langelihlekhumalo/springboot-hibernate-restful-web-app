package com.andile.basicblog.controller;

//import com.andile.basicblog.payload.Post;
import com.andile.basicblog.entity.Post;
import com.andile.basicblog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {
    // Lose coupling | using interface not class
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create post rest endpoint
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save a post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
    }

    // Create get posts rest endpoint
    @Operation(
            summary = "GET all Posts REST API",
            description = "GET all Posts REST API is used to get posts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }


    // Create get post rest endpoint
    @Operation(
            summary = "GET Post by id REST API",
            description = "GET Post by id REST API is used to get a post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable(name="postId") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }


    // Create update post rest endpoint
    @Operation(
            summary = "UPDATE Post by id REST API",
            description = "UPDATE Post by id REST API is used to update a post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post, @PathVariable(name="postId") long id) {
        Post objUpdatePost = postService.updatePost(id, post);
        return new ResponseEntity<>(objUpdatePost, HttpStatus.OK);
    }

    // Create delete rest endpoint
    @Operation(
            summary = "DELETE Post by id REST API",
            description = "DELETE Post by id REST API is used to delete a post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable(name="postId") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
    }
}
