package com.bvb.match.business;

import com.bvb.match.persistence.Match;
import com.bvb.match.persistence.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableJms
public class DeleteMatchImpl implements DeleteMatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteMatchImpl.class);

    private final MatchRepository matchRepository;

    @Override
    public void deleteMatch(long matchId) {
        Match match = matchRepository.findById(matchId).stream().findFirst().orElse(null);

        if (match == null) {
            throw new RuntimeException("Match doesn't exist");
        }

        matchRepository.deleteById(matchId);
    }

    private void deleteMatchByTeamId(long teamId) {
        List<Match> matches = matchRepository.findAllMatchesByHomeTeamIdOrAwayTeamId(teamId, teamId);

        for (Match m : matches) {
            matchRepository.deleteById(m.getId());
        }
    }

    private void deleteMatchByTournamentId(long tournamentId) {
        List<Match> matches = matchRepository.findAllMatchesByTournamentId(tournamentId);

        for (Match m : matches) {
            matchRepository.deleteById(m.getId());
        }
    }

    private void deleteMatchBySeasonId(long seasonId) {
        List<Match> matches = matchRepository.findAllMatchesBySeasonId(seasonId);

        for (Match m : matches) {
            matchRepository.deleteById(m.getId());
        }
    }

    @JmsListener(destination = "deleteteam", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-match-when-team-deletion")
    public void deleteMatchWhenTeamDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        LOGGER.info("Message received from team topic: {}", message);
        deleteMatchByTeamId(Long.parseLong(message));
    }

    @JmsListener(destination = "deletetournament", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-match-when-tournament-deletion")
    public void deleteMatchWhenTournamentDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        LOGGER.info("Message received from tournament topic: {}", message);
        deleteMatchByTournamentId(Long.parseLong(message));
    }

    @JmsListener(destination = "deleteseason", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-match-when-season-deletion")
    public void deleteMatchWhenSeasonDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        LOGGER.info("Message received from season topic: {}", message);
        deleteMatchBySeasonId(Long.parseLong(message));
    }
}
