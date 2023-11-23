package com.andile.basicblog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "User Model Information"
)
public class UserDTO {
    private Long id;

    @Schema(description = "User name")
    private String name;

    @Schema(description = "User email")
    private String email;

//    private String password;
}
