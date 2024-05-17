package com.bvb.team.business;

import com.bvb.team.business.exception.TeamDoesntExistsException;
import com.bvb.team.persistence.Team;
import com.bvb.team.persistence.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteTeamImpl implements DeleteTeamService {
    private final TeamRepository teamRepository;
    private final MediaService mediaService;

    @Override
    public void deleteTeam(long teamId) {
        Team team = teamRepository.findById(teamId).stream().findFirst().orElse(null);

        if (team == null) {
            throw new TeamDoesntExistsException();
        }

        teamRepository.deleteById(teamId);

        try {
            mediaService.deleteImage(team.getLogoId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
