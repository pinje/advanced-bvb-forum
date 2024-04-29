package com.bvb.season.business;

import com.bvb.season.business.exception.SeasonDoesntExistsException;
import com.bvb.season.persistence.Season;
import com.bvb.season.persistence.SeasonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteSeasonImpl implements DeleteSeasonService {
    private final SeasonRepository seasonRepository;

    @Override
    public void deleteSeason(long seasonId) {
        Optional<Season> season = seasonRepository.findById(seasonId);

        if (season.isEmpty()) {
            throw new SeasonDoesntExistsException();
        }

        seasonRepository.deleteById(seasonId);
    }

    // delete related matches
}
