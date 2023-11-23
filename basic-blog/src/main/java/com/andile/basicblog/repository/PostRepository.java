package com.andile.basicblog.repository;

import com.andile.basicblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);

    Boolean existsByTitle(String title);
}
