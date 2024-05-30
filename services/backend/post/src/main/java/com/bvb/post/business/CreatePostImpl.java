package com.bvb.post.business;

import com.bvb.post.domain.CreatePostRequest;
import com.bvb.post.persistence.Post;
import com.bvb.post.persistence.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class CreatePostImpl implements CreatePostService {
    private final PostRepository postRepository;

    @Transactional
    @Override
    public void createPost(CreatePostRequest request) {

        var post = Post.builder()
                .matchId(request.getMatchId())
                .userId(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()))
                .postDate(new Timestamp(System.currentTimeMillis()))
                .review_text(request.getReview())
                .vote(0L)
                .build();

        postRepository.save(post);
    }
}
