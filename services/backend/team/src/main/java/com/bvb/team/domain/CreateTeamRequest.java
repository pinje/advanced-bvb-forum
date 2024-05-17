package com.bvb.team.domain;

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
public class CreateTeamRequest {
    @NotBlank(message = "Team name required")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$", message = "Team name: only letters and numbers allowed")
    private String teamName;

    @NotNull(message = "Team logo required")
    private MultipartFile logo;
}
