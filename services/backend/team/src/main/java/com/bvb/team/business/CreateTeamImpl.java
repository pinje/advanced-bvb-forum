package com.bvb.team.business;

import com.bvb.team.business.exception.TeamAlreadyExistsException;
import com.bvb.team.domain.CreateTeamRequest;
import com.bvb.team.persistence.Team;
import com.bvb.team.persistence.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreateTeamImpl implements CreateTeamService {
    private final TeamRepository teamRepository;
    private final MediaService mediaService;

    @Transactional
    @Override
    public void createTeam(CreateTeamRequest request) {
        if (teamRepository.existsByTeamName(request.getTeamName())) {
            throw new TeamAlreadyExistsException();
        }

        String id = "";

        try {
            id = mediaService.uploadImage(request.getLogo());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload logo", e);
        }

        var team = Team.builder()
                .teamName(request.getTeamName())
                .logoId(id)
                .build();

        teamRepository.save(team);
    }
}
