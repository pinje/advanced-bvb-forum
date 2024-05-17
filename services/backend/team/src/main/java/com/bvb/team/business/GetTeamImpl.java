package com.bvb.team.business;

import com.bvb.team.business.exception.TeamDoesntExistsException;
import com.bvb.team.persistence.Team;
import com.bvb.team.persistence.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetTeamImpl implements GetTeamService{
    private final TeamRepository teamRepository;

    @Override
    public Optional<Team> getTeam(long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new TeamDoesntExistsException();
        }

        return teamRepository.findById(teamId);
    }
}
