package com.bvb.post.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long postId;
    private Timestamp postDate;
    private UserDTO user;
    private String matchId;
    private String vote;
    private String review;
}
