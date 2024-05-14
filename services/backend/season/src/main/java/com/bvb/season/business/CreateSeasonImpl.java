package com.bvb.season.business;

import com.bvb.season.business.exception.SeasonAlreadyExistsException;
import com.bvb.season.domain.CreateSeasonRequest;
import com.bvb.season.persistence.SeasonRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.bvb.season.persistence.Season;

@Service
@AllArgsConstructor
public class CreateSeasonImpl implements CreateSeasonService {
    private final SeasonRepository seasonRepository;

    @Transactional
    @Override
    public void createSeason(CreateSeasonRequest request) {

        if (seasonRepository.existsByStartYear(request.getStartYear())) {
            throw new SeasonAlreadyExistsException();
        }

        if (seasonRepository.existsByEndYear(request.getEndYear())) {
            throw new SeasonAlreadyExistsException();
        }

        var season = Season.builder()
                        .startYear(request.getStartYear())
                        .endYear(request.getEndYear())
                        .build();
        seasonRepository.save(season);
    }
}
