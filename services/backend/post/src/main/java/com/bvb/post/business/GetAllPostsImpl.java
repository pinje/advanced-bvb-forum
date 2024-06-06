package com.bvb.post.business;

import com.bvb.post.domain.GetAllPostsResponse;
import com.bvb.post.persistence.Post;
import com.bvb.post.persistence.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GetAllPostsImpl implements GetAllPostsService {
    private final PostRepository postRepository;

    @Override
    public GetAllPostsResponse getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return new GetAllPostsResponse(posts);
    }
}
