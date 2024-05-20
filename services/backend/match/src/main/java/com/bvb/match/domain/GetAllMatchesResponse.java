package com.bvb.match.domain;

import com.bvb.match.persistence.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMatchesResponse {
    private List<Match> matches;
}
