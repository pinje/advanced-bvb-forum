package com.bvb.tournament.business;

import com.bvb.tournament.domain.GetAllTournamentsResponse;
import com.bvb.tournament.persistence.Tournament;
import com.bvb.tournament.persistence.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllTournamentsImpl implements GetAllTournamentsService {
    private final TournamentRepository tournamentRepository;

    @Override
    public GetAllTournamentsResponse getAllTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();
        return new GetAllTournamentsResponse(tournaments);
    }
}
