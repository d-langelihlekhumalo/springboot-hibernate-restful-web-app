package com.andile.basicblog.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        description = "Comment Model Information"
)

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotEmpty(message = "Commenter's name must not be empty. Please provide the commenter's name")
//    @Column(name = "name")
//    private String name;
//
//    @NotEmpty(message = "Commenter's email must not be empty. Please provide a valid email address")
//    @Email
//    @Column(name = "email")
//    private String email;

    @Schema(description = "Blog Comment Content")
    @NotEmpty(message = "Comment content or body must not be empty")
    @Column(name = "content")
    private String content;

    @Schema(description = "Blog Comment Creation Date")
    @CreationTimestamp
    private Instant date_created;

    @Schema(description = "Blog Comment Updated Date")
    @UpdateTimestamp
    private Instant date_updated;

    // Only fetch related entities when relationship is used
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // Only fetch related entities when relationship is used
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
