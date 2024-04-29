package com.bvb.season;

import com.bvb.season.business.CreateSeasonImpl;
import com.bvb.season.business.exception.SeasonAlreadyExistsException;
import com.bvb.season.domain.CreateSeasonRequest;
import com.bvb.season.persistence.Season;
import com.bvb.season.persistence.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSeasonServiceTest {
    @Mock
    private SeasonRepository seasonRepository;
    @InjectMocks
    private CreateSeasonImpl createSeason;

    @Test
    void Add_ValidSeason_SeasonSavedInRepository() {
        // ARRANGE
        CreateSeasonRequest request = CreateSeasonRequest.builder()
                .startYear("2023")
                .endYear("2024")
                .build();
        when(seasonRepository.existsByStartYear(request.getStartYear())).thenReturn(false);
        when(seasonRepository.existsByEndYear(request.getEndYear())).thenReturn(false);

        // ACT
        createSeason.createSeason(request);

        // ASSERT
        verify(seasonRepository, times(1)).existsByStartYear(request.getStartYear());
        verify(seasonRepository, times(1)).existsByEndYear(request.getEndYear());
        verify(seasonRepository, times(1)).save(any(Season.class));
    }

    @Test
    void Add_UserWithExistingStartYear_ThrowsException() throws SeasonAlreadyExistsException {
        // ARRANGE
        CreateSeasonRequest request = CreateSeasonRequest.builder()
                .startYear("2023")
                .endYear("2024")
                .build();
        when(seasonRepository.existsByStartYear(request.getStartYear())).thenReturn(true);

        // ACT
        ResponseStatusException exception = assertThrows(SeasonAlreadyExistsException.class, () -> createSeason.createSeason(request));

        // ASSERT
        assertEquals("Season already exists", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(seasonRepository, times(1)).existsByStartYear(request.getStartYear());
    }

    @Test
    void Add_UserWithExistingEndYear_ThrowsException() throws SeasonAlreadyExistsException {
        // ARRANGE
        CreateSeasonRequest request = CreateSeasonRequest.builder()
                .startYear("2023")
                .endYear("2024")
                .build();
        when(seasonRepository.existsByStartYear(request.getStartYear())).thenReturn(false);
        when(seasonRepository.existsByEndYear(request.getEndYear())).thenReturn(true);

        // ACT
        ResponseStatusException exception = assertThrows(SeasonAlreadyExistsException.class, () -> createSeason.createSeason(request));

        // ASSERT
        assertEquals("Season already exists", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(seasonRepository, times(1)).existsByEndYear(request.getEndYear());
    }
}