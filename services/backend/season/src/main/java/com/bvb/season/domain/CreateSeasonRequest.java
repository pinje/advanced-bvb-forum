package com.bvb.season.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSeasonRequest {
    @NotBlank(message = "Start year required")
    @Pattern(regexp = "^[0-9]{4}$", message = "Start year: 4 digits")
    private String startYear;

    @NotBlank(message = "End year required")
    @Pattern(regexp = "^[0-9]{4}$", message = "End year: 4 digits")
    private String endYear;
}
