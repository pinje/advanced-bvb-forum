package com.bvb.tournament.business;

import com.bvb.tournament.business.exception.TournamentDoesntExistsException;
import com.bvb.tournament.persistence.Tournament;
import com.bvb.tournament.persistence.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetTournamentImpl implements GetTournamentService {
    private final TournamentRepository tournamentRepository;

    @Override
    public Optional<Tournament> getTournament(long tournamentId) {

        if (!tournamentRepository.existsById(tournamentId)) {
            throw new TournamentDoesntExistsException();
        }

        return tournamentRepository.findById(tournamentId);
    }
}
