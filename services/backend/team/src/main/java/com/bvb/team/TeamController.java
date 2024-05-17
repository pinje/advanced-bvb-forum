package com.bvb.team;

import com.bvb.team.business.CreateTeamService;
import com.bvb.team.domain.CreateTeamRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("team")
@AllArgsConstructor
@Tag(name = "Team Management")
public class TeamController {
    private final CreateTeamService createTeamService;

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @PostMapping()
    public ResponseEntity<Void> createTeam(@ModelAttribute @Valid CreateTeamRequest request) {
        createTeamService.createTeam(request);
        return ResponseEntity.accepted().build();
    }
}
