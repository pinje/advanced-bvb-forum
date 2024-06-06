package com.bvb.team;

import com.bvb.team.business.CreateTeamService;
import com.bvb.team.business.DeleteTeamService;
import com.bvb.team.business.GetAllTeamsService;
import com.bvb.team.business.GetTeamService;
import com.bvb.team.domain.CreateTeamRequest;
import com.bvb.team.domain.GetAllTeamsResponse;
import com.bvb.team.persistence.Team;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("team")
@AllArgsConstructor
@Tag(name = "Team Management")
public class TeamController {
    private final CreateTeamService createTeamService;
    private final DeleteTeamService deleteTeamService;
    private final GetAllTeamsService getAllTeamsService;
    private final GetTeamService getTeamService;

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @PostMapping("protected")
    public ResponseEntity<Void> createTeam(@ModelAttribute @Valid CreateTeamRequest request) {
        createTeamService.createTeam(request);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @GetMapping("protected")
    public ResponseEntity<GetAllTeamsResponse> getAllTeams() {
        return ResponseEntity.ok(getAllTeamsService.getAllTeams());
    }

    @GetMapping("get/{teamId}")
    public ResponseEntity<Optional<Team>> getTeam(@PathVariable(value = "teamId") final long teamId) {
        return ResponseEntity.ok(getTeamService.getTeam(teamId));
    }

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @DeleteMapping("protected/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable long teamId) {
        deleteTeamService.deleteTeam(teamId);
        return ResponseEntity.ok().build();
    }
}
