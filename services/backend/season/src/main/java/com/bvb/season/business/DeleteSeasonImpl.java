package com.bvb.season.business;

import com.azure.messaging.servicebus.*;

import com.bvb.season.business.exception.SeasonDoesntExistsException;
import com.bvb.season.persistence.Season;
import com.bvb.season.persistence.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteSeasonImpl implements DeleteSeasonService {

    @Value("${spring.cloud.azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.servicebus.consumer.entity-name}")
    private String topicName;

    private final SeasonRepository seasonRepository;

    @Override
    public void deleteSeason(long seasonId) {
        Optional<Season> season = seasonRepository.findById(seasonId);

        if (season.isEmpty()) {
            throw new SeasonDoesntExistsException();
        }

        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();
        try {
            senderClient.sendMessage(new ServiceBusMessage(String.valueOf(seasonId)).setSubject("season-id"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }

        seasonRepository.deleteById(seasonId);
    }
}