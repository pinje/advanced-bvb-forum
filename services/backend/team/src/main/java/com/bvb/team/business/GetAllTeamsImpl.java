package com.bvb.team.business;

import com.bvb.team.domain.GetAllTeamsResponse;
import com.bvb.team.persistence.Team;
import com.bvb.team.persistence.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllTeamsImpl implements GetAllTeamsService{
    private final TeamRepository teamRepository;

    @Override
    public GetAllTeamsResponse getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return new GetAllTeamsResponse(teams);
    }
}
