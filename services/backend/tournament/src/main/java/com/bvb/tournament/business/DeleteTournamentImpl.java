package com.bvb.tournament.business;

import com.bvb.tournament.business.exception.TournamentDoesntExistsException;
import com.bvb.tournament.persistence.Tournament;
import com.bvb.tournament.persistence.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteTournamentImpl implements DeleteTournamentService {
    private final TournamentRepository tournamentRepository;
    private final MediaService mediaService;

    @Override
    public void deleteTournament(long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).stream().findFirst().orElse(null);

        if (tournament == null) {
            throw new TournamentDoesntExistsException();
        }

        tournamentRepository.deleteById(tournamentId);

        try {
            mediaService.deleteImage(tournament.getLogoId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
