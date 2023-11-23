package com.andile.basicblog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        description = "Comment Model Information"
)
public class CommentDTO {
//    private long id;
//
////    @NotEmpty(message = "Commenter's name must not be empty. Please provide the commenter's name")
//    private String name;
////
//////    @NotEmpty(message = "Commenter's email must not be empty. Please provide a valid email address")
//////    @Email
//    private String email;
//
//    @NotEmpty(message = "Comment content or body must not be empty")
//    private String content;
    private long id;

    @Schema(description = "Blog Comment Content")
    @NotEmpty(message = "Comment content or body must not be empty")
    private String content;

    @Schema(description = "Blog Comment Creation Date")
    private Instant date_created;

    @Schema(description = "Blog Comment Updated Date")
    private Instant date_updated;
}
