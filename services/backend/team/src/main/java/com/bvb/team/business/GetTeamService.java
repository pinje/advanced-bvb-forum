package com.bvb.team.business;

import com.bvb.team.persistence.Team;

import java.util.Optional;

public interface GetTeamService {
    Optional<Team> getTeam(long teamId);
}
