package com.bvb.match.business;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.bvb.match.persistence.Match;
import com.bvb.match.persistence.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableJms
public class DeleteMatchImpl implements DeleteMatchService {

    private final MatchRepository matchRepository;

    @Value("${spring.cloud.azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.servicebus.consumer.entity-name}")
    private String topicName;

    @Override
    public void deleteMatch(long matchId) {
        Match match = matchRepository.findById(matchId).stream().findFirst().orElse(null);

        if (match == null) {
            throw new RuntimeException("Match doesn't exist");
        }

        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();
        try {
            senderClient.sendMessage(new ServiceBusMessage(String.valueOf(matchId)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }

        matchRepository.deleteById(matchId);
    }

    private void deleteMatchByTeamId(long teamId) {
        List<Match> matches = matchRepository.findAllMatchesByHomeTeamIdOrAwayTeamId(teamId, teamId);

        for (Match m : matches) {
            matchRepository.deleteById(m.getId());
        }
    }

    private void getMatchesByTeamId(long teamId) {
        List<Match> matches = matchRepository.findAllMatchesByHomeTeamIdOrAwayTeamId(teamId, teamId);
        sendDeletion(matches);
    }

    private void deleteMatchByTournamentId(long tournamentId) {
        List<Match> matches = matchRepository.findAllMatchesByTournamentId(tournamentId);

        for (Match m : matches) {
            matchRepository.deleteById(m.getId());
        }
    }

    private void getMatchesByTournamentId(long tournamentId) {
        List<Match> matches = matchRepository.findAllMatchesByTournamentId(tournamentId);
        sendDeletion(matches);
    }

    private void deleteMatchBySeasonId(long seasonId) {
        List<Match> matches = matchRepository.findAllMatchesBySeasonId(seasonId);

        for (Match m : matches) {
            matchRepository.deleteById(m.getId());
        }
    }

    private void getMatchesBySeasonId(long seasonId) {
        List<Match> matches = matchRepository.findAllMatchesBySeasonId(seasonId);
        sendDeletion(matches);
    }

    private void sendDeletion(List<Match> matches) {
        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();

        for (Match m : matches) {
            try {
                senderClient.sendMessage(new ServiceBusMessage(String.valueOf(m.getId())));
            } catch (Exception e) {
                throw new RuntimeException("Failed to send message", e);
            }
        }
    }

    @JmsListener(destination = "deleteteam", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-match-when-team-deletion")
    public void deleteMatchWhenTeamDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        deleteMatchByTeamId(Long.parseLong(message));
    }

    @JmsListener(destination = "deleteteam", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "get-related-matches-when-team-deletion")
    public void getMatchesWhenTeamDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        getMatchesByTeamId(Long.parseLong(message));
    }

    @JmsListener(destination = "deletetournament", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-match-when-tournament-deletion")
    public void deleteMatchWhenTournamentDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        deleteMatchByTournamentId(Long.parseLong(message));
    }

    @JmsListener(destination = "deletetournament", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "get-related-matches-when-tournament-deletion")
    public void getMatchesWhenTournamentDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        getMatchesByTournamentId(Long.parseLong(message));
    }

    @JmsListener(destination = "deleteseason", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "delete-match-when-season-deletion")
    public void deleteMatchWhenSeasonDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        deleteMatchBySeasonId(Long.parseLong(message));
    }

    @JmsListener(destination = "deleteseason", containerFactory = "topicJmsListenerContainerFactory",
            subscription = "get-related-matches-when-season-deletion")
    public void getMatchesWhenSeasonDeletion(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        getMatchesBySeasonId(Long.parseLong(message));
    }
}
