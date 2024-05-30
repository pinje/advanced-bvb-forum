package com.bvb.post.business;

import com.bvb.post.domain.GetAllPostsResponse;
import com.bvb.post.persistence.Post;
import com.bvb.post.persistence.PostDTO;
import com.bvb.post.persistence.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllPostsImpl implements GetAllPostsService {
    private final PostRepository postRepository;

    @Override
    public GetAllPostsResponse getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> = List<>();
        for (Post post : posts) {

        }

        return new GetAllPostsResponse(posts);
    }
}
