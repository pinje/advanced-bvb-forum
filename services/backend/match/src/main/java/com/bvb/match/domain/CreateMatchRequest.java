package com.bvb.match.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMatchRequest {
    @NotNull(message = "Match date required")
    private Long matchDate;

    @NotNull(message = "Season required")
    private Long seasonId;

    @NotNull(message = "Tournament required")
    private Long tournamentId;

    @NotNull(message = "Home team required")
    private Long homeTeamId;

    @NotNull(message = "Away team required")
    private Long awayTeamId;

    @NotBlank(message = "Home team score required")
    @Length(max = 3, message = "Home team score: max 3 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Home team score: only numbers allowed")
    private String homeTeamScore;

    @NotBlank(message = "Away team score required")
    @Length(max = 3, message = "Away team score: max 3 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Away team score: only numbers allowed")
    private String awayTeamScore;
}
