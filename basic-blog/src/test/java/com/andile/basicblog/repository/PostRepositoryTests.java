package com.andile.basicblog.repository;

import com.andile.basicblog.entity.Post;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

//@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepository;

    // JUnit test for saving a post
    @DisplayName("JUnit test for saving a post")
    @Test
    public void givenPostObject_whenSave_thenReturnSavedPost() {
        // Given object setup
        Post post = Post.builder()
                .title("First Blog Post")
                .content("First Blog Post Content")
                .build();

        // When saving an object
        Post savedPost = postRepository.save(post);

        // Then verify saved object
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isGreaterThan(0);
    }

    // Junit test to get all posts
    @DisplayName("Junit test to get all posts")
    @Test
    public void givenPostList_whenFindAll_thenPostsList() {
        // Given object setup
        Post objPost1 = Post.builder()
                .title("First Blog Post")
                .content("First Blog Post Content")
                .build();

        Post objPost2 = Post.builder()
                .title("Second Blog Post")
                .content("Second Blog Post Content")
                .build();

        Post objPost3 = Post.builder()
                .title("Third Blog Post")
                .content("Third Blog Post Content")
                .build();

        int numExistingPosts = postRepository.findAll().size();

        postRepository.save(objPost1);
        postRepository.save(objPost2);
        postRepository.save(objPost3);

        // When saving an object
        List<Post> postList = postRepository.findAll();

        // Then verify saved object
        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(numExistingPosts + 3);
    }

    // JUnit test to get post by id
    @DisplayName("JUnit test to get post by id")
    @Test
    public void givenPostObject_whenFindById_thenReturnPostObject() {
        // Given object setup
        Post objPost = Post.builder()
                .title("First Blog Post")
                .content("First Blog Post Content")
                .build();

        postRepository.save(objPost);

        // When saving an object
        Post postDB = postRepository.findById(objPost.getId()).get();

        // Then verify saved object
        assertThat(postDB).isNotNull();

    }

    // JUnit test to update post
    @DisplayName("JUnit test to update post")
    @Test
    public void givenPostObject_whenUpdatePost_thenReturnUpdatedPostObject() {
        // Given object setup
        Post objPost = Post.builder()
                .title("First Blog Post")
                .content("First Blog Post Content")
                .build();

        postRepository.save(objPost);

        // When saving an object
        Post postDB = postRepository.findById(objPost.getId()).get();
        postDB.setContent("Updated the content's of the first post");
        Post updatedPost = postRepository.save(postDB);

        // Then verify saved object
        assertThat(postDB).isNotNull();
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getContent()).isEqualTo("Updated the content's of the first post");

    }

    // JUnit test to delete post
    @DisplayName("JUnit test to delete post")
    @Test
    public void givenPostObject_whenDeletePost_thenReturnRemovePost() {
        // Given object setup
        Post objPost = Post.builder()
                .title("First Blog Post")
                .content("First Blog Post Content")
                .build();

        postRepository.save(objPost);

        // When saving an object
        postRepository.delete(objPost);
        Optional<Post> post = postRepository.findById(objPost.getId());

        // Then verify saved object
        assertThat(post).isEmpty();

    }
}
