package com.bvb.season.domain;

import com.bvb.season.persistence.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllSeasonsResponse {
    private List<Season> seasons;
}
