package com.bvb.match;

import com.bvb.match.business.CreateMatchService;
import com.bvb.match.business.DeleteMatchService;
import com.bvb.match.business.GetAllMatchesService;
import com.bvb.match.domain.CreateMatchRequest;
import com.bvb.match.domain.GetAllMatchesResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("match")
@AllArgsConstructor
@Tag(name = "Match Management")
public class MatchController {
    private final CreateMatchService createMatchService;
    private final DeleteMatchService deleteMatchService;
    private final GetAllMatchesService getAllMatchesService;

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @PostMapping
    public ResponseEntity<Void> createMatch(@RequestBody @Valid CreateMatchRequest createMatchRequest) {
        createMatchService.createMatch(createMatchRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("{seasonId}/{tournamentId}")
    public ResponseEntity<GetAllMatchesResponse> getAllMatchesBySeasonAndByTournament(
            @PathVariable(value = "seasonId") final long seasonId,
            @PathVariable(value = "tournamentId") final long tournamentId
    ) {
        return ResponseEntity.ok(getAllMatchesService.getAllMatchesBySeasonAndTournament(seasonId, tournamentId));
    }

    @GetMapping("{seasonId}")
    public ResponseEntity<GetAllMatchesResponse> getAllMatchesBySeason(
            @PathVariable(value = "seasonId") final long seasonId
    ) {
        return ResponseEntity.ok(getAllMatchesService.getAllMatchesBySeason(seasonId));
    }

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @DeleteMapping("{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable int matchId) {
        deleteMatchService.deleteMatch(matchId);
        return ResponseEntity.ok().build();
    }
}
