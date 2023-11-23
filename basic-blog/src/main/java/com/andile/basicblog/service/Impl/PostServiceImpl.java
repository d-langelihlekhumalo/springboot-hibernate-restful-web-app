package com.andile.basicblog.service.Impl;

import com.andile.basicblog.entity.Post;
import com.andile.basicblog.exception.ResourceExistsException;
import com.andile.basicblog.exception.ResourceNotFoundException;
//import com.andile.basicblog.payload.Post;
import com.andile.basicblog.repository.PostRepository;
import com.andile.basicblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        // Set id to 0 if id exists in request body
        post.setId(0L);
        if (postRepository.existsByTitle(post.getTitle())) {
            Optional<Post> objPost = postRepository.findByTitle(post.getTitle());
            if (objPost.isPresent()) {
                if (objPost.get().getContent().equalsIgnoreCase(post.getContent())) {
                    throw new ResourceExistsException("Post", "title", post.getTitle());
                }
            }
        }
        // Save and return Post object
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(long id) {
        // Get post by id from database | throw resourceNotFoundException
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
    }

    @Override
    public Post updatePost(long id, Post post) {
        // Get post by id from database | throw resourceNotFoundException
        Post objPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        // Check if updated post is the same as current post
        if (objPost.getTitle().equalsIgnoreCase(post.getTitle())) {
            if (objPost.getContent().equalsIgnoreCase(post.getContent())) {
                throw new ResourceExistsException("Post", "title", post.getTitle());
            }
        }

        objPost.setTitle(post.getTitle());
        objPost.setContent(post.getContent());

        return postRepository.save(objPost);
    }

    @Override
    public void deletePostById(long id) {
        // Get post by id from database | throw resourceNotFoundException
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.delete(post);
    }

}
