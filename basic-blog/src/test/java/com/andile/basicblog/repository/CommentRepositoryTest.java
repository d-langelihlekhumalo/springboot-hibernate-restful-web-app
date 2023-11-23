package com.andile.basicblog.repository;

import com.andile.basicblog.entity.Comment;
import com.andile.basicblog.entity.Post;
import static org.assertj.core.api.Assertions.assertThat;

import com.andile.basicblog.entity.User;
import com.andile.basicblog.exception.ResourceNotFoundException;
import com.andile.basicblog.payload.CommentDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // JUnit test for saving a comment
    @DisplayName("JUnit test for saving a comment")
    @Test
    public void givenCommentObject_whenSave_thenReturnSavedComment() {
        // Given object setup
        Comment comment = getDefaultComment();

        // When saving an object
        Comment savedComment = commentRepository.save(comment);

        // Then verify saved object
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isGreaterThan(0);
    }

    // JUnit test to get all comments
    @DisplayName("JUnit test to get all comments")
    @Test
    public void givenCommentList_whenFindAll_thenPostsList() {
        // Given object setup
        Post objPost = Post.builder()
                .title("Manchester City's Kevin De Bruyne")
                .content("Magistic player! Absolute club legend")
                .build();

        User objUser = User.builder()
                .name("Kevin De Bruyne")
                .email("kevin@email.com")
                .password(passwordEncoder.encode("bruyne"))
                .build();

        CommentDTO objCommentDTO = CommentDTO.builder()
                .content("Yeah,Kevin is superb. Fantastic player")
                .build();

        int numExistingComments = commentRepository.findAll().size();

        Comment comment2 = mapToEntity(objCommentDTO);

        Post objSavedPost2 = postRepository.save(objPost);
        User objSavedUser2 = userRepository.save(objUser);

        comment2.setPost(objSavedPost2);
        comment2.setUser(objSavedUser2);

        Comment comment = getDefaultComment();
        Comment savedComment1 = commentRepository.save(comment);
        Comment savedComment2 = commentRepository.save(comment2);

        // When saving an object
        List<Comment> commentList = commentRepository.findAll();

        // Then verify saved object

        // Check that the saved comment entities are not null
        assertThat(savedComment1).isNotNull();
        assertThat(savedComment2).isNotNull();
        // Check that the saved comment entities id's are greater than 1
        assertThat(savedComment1.getId()).isGreaterThan(0);
        assertThat(savedComment2.getId()).isGreaterThan(0);
        // Check that the retrieved comments are not null
        assertThat(commentList).isNotNull();
        assertThat(commentList.size()).isEqualTo(numExistingComments + 2);
    }

    // JUnit test to get comment by id
    @DisplayName("JUnit test to get comment by id")
    @Test
    public void givenCommentObject_whenFindById_thenReturnCommentObject() {
        // Given object setup
        Comment comment = getDefaultComment();
        commentRepository.save(comment);
        // When saving an object
        Comment commentDB = commentRepository.findById(comment.getId()).get();

        // Then verify saved object
        assertThat(commentDB).isNotNull();

    }

    // JUnit test to get comment by post id
    @DisplayName("JUnit test to get comment by post id")
    @Test
    public void givenCommentObject_whenFindByPostId_thenReturnCommentObject() {
        // Given object setup
        Comment comment = getDefaultComment();
        commentRepository.save(comment);
        // When saving an object
        List<Comment> commentDBs = commentRepository.findByPostId(comment.getPost().getId());

        // Then verify saved object
        assertThat(commentDBs).isNotNull();
        assertThat(commentDBs.size()).isGreaterThan(0);

    }

    // JUnit test to update comment
    @DisplayName("JUnit test to update comment")
    @Test
    public void givenCommentObject_whenUpdatePost_thenReturnUpdatedCommentObject() {
        // Given object setup
        Comment comment = getDefaultComment();
        commentRepository.save(comment);

        // When saving an object
        Comment commentDB = commentRepository.findById(comment.getId()).get();
        commentDB.setContent("If he continues on playing like this, break the all time goal scoring record in EPL");
        Comment updatedComment = commentRepository.save(commentDB);

        // Then verify saved object
        assertThat(commentDB).isNotNull();
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.getContent())
                .isEqualTo("If he continues on playing like this, break the all time goal scoring record in EPL");

    }

//    // JUnit test to delete comment
    @DisplayName("JUnit test to delete comment")
    @Test
    public void givenCommentObject_whenDeletePost_thenReturnRemovePost() {
        // Given object setup
        Comment comment = getDefaultComment();
        commentRepository.save(comment);

        // When saving an object
        commentRepository.delete(comment);
        Optional<Comment> objDeletedComment = commentRepository.findById(comment.getId());

        // Then verify saved object
        assertThat(objDeletedComment).isEmpty();

    }


    // Returns a default comment object
    private Comment getDefaultComment() {
        Post post = Post.builder()
                .title("Manchester City's Erling Haaland")
                .content("First season record breaker, won major honours")
                .build();

        User user = User.builder()
                .name("Erling Haaland")
                .email("erling.haaland@email.com")
                .password(passwordEncoder.encode("haaland"))
                .build();

        CommentDTO commentDTO = CommentDTO.builder()
                .content("Yeah, Erling was superb last season. Fantastic player")
                .build();

        Comment comment = mapToEntity(commentDTO);

        Post objPost = postRepository.save(post);
        User objUser = userRepository.save(user);

        comment.setPost(objPost);
        comment.setUser(objUser);

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
