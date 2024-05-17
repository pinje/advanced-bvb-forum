package com.bvb.tournament.domain;

import com.bvb.tournament.persistence.Tournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTournamentsResponse {
    private List<Tournament> tournaments;
}
