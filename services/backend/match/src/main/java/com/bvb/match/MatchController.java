package com.bvb.match;

import com.bvb.match.business.CreateMatchService;
import com.bvb.match.domain.CreateMatchRequest;
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
@RequestMapping("match")
@AllArgsConstructor
@Tag(name = "Match Management")
public class MatchController {
    private final CreateMatchService createMatchService;

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @PostMapping
    public ResponseEntity<Void> createMatch(@RequestBody @Valid CreateMatchRequest createMatchRequest) {
        createMatchService.createMatch(createMatchRequest);
        return ResponseEntity.accepted().build();
    }
}
