package com.andile.basicblog.controller;

//import com.andile.basicblog.payload.Comment;
import com.andile.basicblog.entity.Comment;
import com.andile.basicblog.payload.CommentDTO;
import com.andile.basicblog.service.CommentService;
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
@RequestMapping("/api")
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save a comment"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name="postId") long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO, Principal principal) {
        // @PathVariable(value="userId") long userId, | userId,
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO, principal), HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET all Comments REST API",
            description = "GET all Comments REST API is used to get comments"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(name="postId") long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @Operation(
            summary = "GET Comment by id REST API",
            description = "GET Comment by id REST API is used to get a comment by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value="postId") long postId, @PathVariable(value="commentId") long id) {
        CommentDTO comment = commentService.getCommentById(postId, id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Operation(
            summary = "UPDATE Comment by id REST API",
            description = "UPDATE Comment by id REST API is used to update a comment by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value="postId") long postId,
                           @PathVariable(value="commentId") long id, @Valid @RequestBody CommentDTO commentDTO,
                                                    Principal principal) {
        return new ResponseEntity<>(commentService.updateComment(postId, id, commentDTO, principal), HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE Comment by id REST API",
            description = "DELETE Comment by id REST API is used to delete a comment by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value="postId") long postId,
                                                @PathVariable(value="commentId") long id, Principal principal) {
        commentService.deleteComment(postId, id, principal);
        return new ResponseEntity<>("Comment successfully deleted!", HttpStatus.OK);
    }
}
