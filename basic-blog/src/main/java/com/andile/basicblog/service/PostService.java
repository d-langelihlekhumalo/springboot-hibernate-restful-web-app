package com.andile.basicblog.service;

//import com.andile.basicblog.payload.Post;

import com.andile.basicblog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post createPost(Post post);

    List<Post> getAllPosts();

    Post getPostById(long id);

    Post updatePost(long id, Post post);

    void deletePostById(long id);
}
