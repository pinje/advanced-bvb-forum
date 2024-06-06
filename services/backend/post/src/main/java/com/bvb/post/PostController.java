package com.bvb.post;

import com.bvb.post.business.CreatePostService;
import com.bvb.post.business.DeletePostService;
import com.bvb.post.business.GetAllPostsService;
import com.bvb.post.domain.CreatePostRequest;
import com.bvb.post.domain.GetAllPostsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post")
@AllArgsConstructor
@Tag(name = "Post Management")
public class PostController {
    private final CreatePostService createPostService;
    private final GetAllPostsService getAllPostsService;
    private final DeletePostService deletePostService;

    @PreAuthorize("hasAnyAuthority('[MEMBER]','[ADMIN]')")
    @PostMapping("protected")
    public ResponseEntity<Void> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        createPostService.createPost(createPostRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("get")
    public ResponseEntity<GetAllPostsResponse> getAllPosts() {
        return ResponseEntity.ok(getAllPostsService.getAllPosts());
    }

    @PreAuthorize("hasAnyAuthority('[MEMBER]','[ADMIN]')")
    @DeleteMapping("protected/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable long postId) {
        deletePostService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
