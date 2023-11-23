package com.andile.basicblog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should at least be 2 characters long")
    private String title;

    @NotEmpty
    @Size(min = 5, message = "Post content should at least be 5 characters long")
    private String content;
}
