package com.bvb.team.domain;

import com.bvb.team.persistence.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTeamsResponse {
    private List<Team> teams;
}
