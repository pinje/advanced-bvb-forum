package com.bvb.post;

import com.bvb.post.business.CreatePostService;
import com.bvb.post.domain.CreatePostRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("post")
@AllArgsConstructor
@Tag(name = "Match Management")
public class PostController {
    private final CreatePostService createPostService;

    @PreAuthorize("hasAnyAuthority('[MEMBER]','[ADMIN]')")
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        createPostService.createPost(createPostRequest);
        return ResponseEntity.accepted().build();
    }
}
