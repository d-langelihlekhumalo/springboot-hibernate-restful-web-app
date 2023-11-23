package com.andile.basicblog.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        description = "Post Model Information"
)

@Entity
@Table(name="posts", uniqueConstraints ={@UniqueConstraint(columnNames = {"title"})} )
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Blog Post Title")
    @NotEmpty
    @Size(min = 2, message = "Post title should at least be 2 characters long")
    @Column(name="title", nullable = false)
    private String title;

    @Schema(description = "Blog Post Description")
    @NotEmpty
    @Size(min = 5, message = "Post content should at least be 5 characters long")
    @Column(name="content", nullable = false)
    private String content;

    @Schema(description = "Blog Post Creation Date")
    @CreationTimestamp
    private Instant date_created;

    @Schema(description = "Blog Post Updated Date")
    @UpdateTimestamp
    private Instant date_updated;

    @Schema(description = "Blog Post Comments")
    // Only contain unique comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

}
