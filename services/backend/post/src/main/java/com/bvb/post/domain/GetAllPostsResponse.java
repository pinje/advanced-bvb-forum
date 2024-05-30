package com.bvb.post.domain;

import com.bvb.post.persistence.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPostsResponse {
    private List<PostDTO> posts;
}
