package com.bvb.tournament.business;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.bvb.tournament.business.exception.TournamentDoesntExistsException;
import com.bvb.tournament.persistence.Tournament;
import com.bvb.tournament.persistence.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTournamentImpl implements DeleteTournamentService {
    @Value("${spring.cloud.azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.servicebus.consumer.entity-name}")
    private String topicName;

    private final TournamentRepository tournamentRepository;
    private final MediaService mediaService;

    @Override
    public void deleteTournament(long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).stream().findFirst().orElse(null);

        if (tournament == null) {
            throw new TournamentDoesntExistsException();
        }

        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();
        try {
            senderClient.sendMessage(new ServiceBusMessage(String.valueOf(tournamentId)).setSubject("tournament-id"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }

        tournamentRepository.deleteById(tournamentId);

        try {
            mediaService.deleteImage(tournament.getLogoId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }
}
