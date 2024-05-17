package com.bvb.tournament;

import com.bvb.tournament.business.CreateTournamentService;
import com.bvb.tournament.business.DeleteTournamentService;
import com.bvb.tournament.business.GetAllTournamentsService;
import com.bvb.tournament.business.GetTournamentService;
import com.bvb.tournament.domain.CreateTournamentRequest;
import com.bvb.tournament.domain.GetAllTournamentsResponse;
import com.bvb.tournament.persistence.Tournament;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

@RestController
@RequestMapping("tournament")
@AllArgsConstructor
@Tag(name = "Tournament Management")
public class TournamentController {
    private final CreateTournamentService createTournamentService;
    private final DeleteTournamentService deleteTournamentService;
    private final GetAllTournamentsService getAllTournamentsService;
    private final GetTournamentService getTournamentService;

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @PostMapping()
    public ResponseEntity<Void> createTournament(@ModelAttribute @Valid CreateTournamentRequest request) {
        createTournamentService.createTournament(request);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasAnyAuthority('[MEMBER]','[ADMIN]')")
    @GetMapping
    public ResponseEntity<GetAllTournamentsResponse> getAllTournaments() {
        return ResponseEntity.ok(getAllTournamentsService.getAllTournaments());
    }

    @PreAuthorize("hasAnyAuthority('[MEMBER]','[ADMIN]')")
    @GetMapping("{tournamentId}")
    public ResponseEntity<Optional<Tournament>> getTournament(@PathVariable(value = "tournamentId") final long tournamentId) {
        return ResponseEntity.ok(getTournamentService.getTournament(tournamentId));
    }

    @PreAuthorize("hasAuthority('[ADMIN]')")
    @DeleteMapping("{tournamentId}")
    public ResponseEntity<Void> deleteTournament(@PathVariable long tournamentId) {
        deleteTournamentService.deleteTournament(tournamentId);
        return ResponseEntity.ok().build();
    }
}
