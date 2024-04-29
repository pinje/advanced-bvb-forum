package com.bvb.season.business;

import com.bvb.season.domain.GetAllSeasonsResponse;
import com.bvb.season.persistence.Season;
import com.bvb.season.persistence.SeasonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllSeasonsImpl implements GetAllSeasonsService {
    private final SeasonRepository seasonRepository;

    @Override
    public GetAllSeasonsResponse getAllSeasons() {
        List<Season> seasons = seasonRepository.findAll();
        return new GetAllSeasonsResponse(seasons);
    }

    // retrieve related matches
}
