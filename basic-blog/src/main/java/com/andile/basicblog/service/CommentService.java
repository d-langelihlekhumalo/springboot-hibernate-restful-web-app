package com.andile.basicblog.service;

//import com.andile.basicblog.payload.Comment;

import com.andile.basicblog.entity.Comment;
import com.andile.basicblog.payload.CommentDTO;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO, Principal principal);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO, Principal principal);

    void deleteComment(long postId, long commentId, Principal principal);
}
