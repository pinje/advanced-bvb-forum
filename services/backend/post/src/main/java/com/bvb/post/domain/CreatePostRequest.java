package com.bvb.post.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    @NotNull(message = "Match required")
    private Long matchId;

    @NotBlank(message = "Review text required")
    @Length(max = 2000, message = "Review: max 2000 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,?!'\":;()\\-]*$",
            message = "Your review contains invalid characters. Please use only letters, numbers, spaces, and the following punctuation: . , ? ! ' \" : ; ( ) -")
    private String review;
}
