package com.andile.basicblog.service.Impl;

import com.andile.basicblog.entity.Comment;
import com.andile.basicblog.entity.Post;
import com.andile.basicblog.entity.User;
import com.andile.basicblog.exception.APIException;
import com.andile.basicblog.exception.ResourceExistsException;
import com.andile.basicblog.exception.ResourceNotFoundException;
import com.andile.basicblog.payload.CommentDTO;
import com.andile.basicblog.repository.CommentRepository;
import com.andile.basicblog.repository.PostRepository;
import com.andile.basicblog.repository.UserRepository;
import com.andile.basicblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO, Principal principal) {
        Comment comment = mapToEntity(commentDTO);
        // Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        // Verify user authentication before creating comment
        User objUser = userRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", principal.getName()));

        // Set post to comment
        comment.setPost(post);
        // Set user into to comment
        comment.setUser(objUser);

        // Save comment
        Comment objCommentSaved = commentRepository.save(comment);
        return mapToDTO(objCommentSaved);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        // Retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        // Convert list of comments to DTO "entities to DTO"
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Comment comment = getComment(postId, commentId);
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO, Principal principal) {
        Comment comment = getComment(postId, commentId);

        // Verify user authentication before creating comment
        User objUser = userRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", principal.getName()));

        // Check if comment belongs to user
        if (objUser.getId() != comment.getUser().getId()) {
            throw new ResourceNotFoundException("Comment", "id", String.valueOf(commentId));
        }

        // Check if comment already exists
        List<CommentDTO> commentDTOList = getCommentsByPostId(postId);
        boolean isCommentAlreadyExists = false;
        for (CommentDTO objCommentDTO : commentDTOList) {
            if (comment.getContent().equalsIgnoreCase(commentDTO.getContent())) {
                if (objUser.getId() == comment.getUser().getId()) {
                    isCommentAlreadyExists = true;
                    break;
                }
            }
        }

        if (isCommentAlreadyExists) {
            throw new ResourceExistsException("Comment", "content", comment.getContent());
        }

        // Update comment
        comment.setContent(commentDTO.getContent());
        return mapToDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long postId, long commentId, Principal principal) {
        Comment comment = getComment(postId, commentId);

        // Verify user authentication before creating comment
        User objUser = userRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", principal.getName()));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = String.valueOf(auth.getAuthorities());

        if (!(role.equalsIgnoreCase("[ROLE_ADMIN]"))) {
            // Check if comment belongs to user before allowing deletion
            if (objUser.getId() != comment.getUser().getId()) {
                throw new ResourceNotFoundException("Comment", "id", String.valueOf(commentId));
            }
        }

        commentRepository.delete(comment);
    }

    private Comment getComment(long postId, long commentId) {
        // Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        // Retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        // Check if comment belong to post
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return comment;
    }

    // Convert entity to DTO
    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate_created(comment.getDate_created());
        commentDTO.setDate_updated(comment.getDate_updated());
        commentDTO.setContent(comment.getContent());
        return commentDTO;
    }

    // Convert DTO to entity
    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDate_created(commentDTO.getDate_created());
        comment.setDate_updated(commentDTO.getDate_updated());
        comment.setContent(commentDTO.getContent());
        return comment;
    }

}
