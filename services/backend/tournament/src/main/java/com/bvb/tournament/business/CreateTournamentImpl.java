package com.bvb.tournament.business;

import com.bvb.tournament.business.exception.TournamentAlreadyExistsException;
import com.bvb.tournament.domain.CreateTournamentRequest;
import com.bvb.tournament.persistence.CategoryEnum;
import com.bvb.tournament.persistence.Tournament;
import com.bvb.tournament.persistence.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreateTournamentImpl implements CreateTournamentService {

    private final TournamentRepository tournamentRepository;
    private final MediaService mediaService;

    @Transactional
    @Override
    public void createTournament(CreateTournamentRequest request) {

        if (tournamentRepository.existsByTournamentName(request.getTournamentName())) {
            throw new TournamentAlreadyExistsException();
        }

        String id;

        try {
            id = mediaService.uploadImage(request.getLogo());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        var tournament = Tournament.builder()
                .tournamentName(request.getTournamentName())
                .logoId(id)
                .category(CategoryEnum.valueOf(request.getCategory()))
                .build();

        tournamentRepository.save(tournament);
    }
}
