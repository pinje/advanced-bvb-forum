package com.bvb.post.business;

import com.bvb.post.persistence.Post;
import com.bvb.post.persistence.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
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

    @JmsListener(destination = "deletematch", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-post-when-match-deletion")
    public void deletePostWhenMatchDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        deletePostByMatchId(Long.parseLong(message));
    }

    private void deletePostByMatchId(long matchId) {
        List<Post> posts = postRepository.findAllPostsByMatchId(matchId);

        for (Post p : posts) {
            postRepository.deleteById(p.getId());
        }
    }

    @JmsListener(destination = "deleteuser", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-post-when-user-deletion")
    public void deletePostWhenUserDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        deletePostByUserId(Long.parseLong(message));
    }

    private void deletePostByUserId(long userId) {
        List<Post> posts = postRepository.findAllPostsByUserId(userId);

        for (Post p : posts) {
            postRepository.deleteById(p.getId());
        }
    }
}
