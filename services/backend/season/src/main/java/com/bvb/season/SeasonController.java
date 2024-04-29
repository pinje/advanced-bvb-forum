package com.bvb.season;

import com.bvb.season.business.CreateSeasonService;
import com.bvb.season.business.DeleteSeasonService;
import com.bvb.season.business.GetAllSeasonsService;
import com.bvb.season.domain.CreateSeasonRequest;
import com.bvb.season.domain.GetAllSeasonsResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("season")
@AllArgsConstructor
@Tag(name = "Season Management")

public class SeasonController {
    private final CreateSeasonService createSeasonService;
    private final GetAllSeasonsService getAllSeasonsService;
    private final DeleteSeasonService deleteSeasonService;

    @PostMapping()
    public ResponseEntity<Void> createSeason(@RequestBody @Valid CreateSeasonRequest request) {
        createSeasonService.createSeason(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<GetAllSeasonsResponse> getAllSeasons() {
        return ResponseEntity.ok(getAllSeasonsService.getAllSeasons());
    }

    @DeleteMapping("{seasonId}")
    public ResponseEntity<Void> deleteSeason(@PathVariable long seasonId) {
        deleteSeasonService.deleteSeason(seasonId);
        return ResponseEntity.ok().build();
    }
}
