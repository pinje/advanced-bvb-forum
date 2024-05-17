package com.bvb.tournament.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTournamentRequest {
    @NotBlank(message = "Tournament name required")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$", message = "Tournament name: only letters and numbers allowed")
    private String tournamentName;

    @NotNull(message = "Tournament logo required")
    private MultipartFile logo;

    @NotEmpty(message = "Tournament category required")
    private String category;
}
