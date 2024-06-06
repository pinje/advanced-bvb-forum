package com.bvb.post.business;

import com.bvb.post.persistence.Post;
import com.bvb.post.persistence.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeletePostImpl implements DeletePostService {
    private final PostRepository postRepository;

    @Override
    public void deletePost(long postId) {
        Post post = postRepository.findById(postId).stream().findFirst().orElse(null);

        if (post == null) {
            throw new RuntimeException("Post doesn't exist");
        }

        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        if (!role.equals("[[ADMIN]]")) {
            if (!Objects.equals(userId, post.getUserId().toString())) {
                throw new RuntimeException("User doesn't match with post publisher");
            }
        }

        postRepository.deleteById(postId);
    }
}
