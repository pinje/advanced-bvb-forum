package com.bvb.season.business;

import com.azure.messaging.servicebus.*;

import com.bvb.season.SeasonApplication;
import com.bvb.season.business.exception.SeasonDoesntExistsException;
import com.bvb.season.persistence.Season;
import com.bvb.season.persistence.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableJms
public class DeleteSeasonImpl implements DeleteSeasonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSeasonImpl.class);
    private static final String TOPIC_NAME = "bvbforumtopic";
    private static final String SUBSCRIPTION_NAME = "tournament-logo";

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
        senderClient.sendMessage(new ServiceBusMessage("yea").setSubject("tournament-logo"));

        seasonRepository.deleteById(seasonId);
    }

    @JmsListener(destination = TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory",
            subscription = SUBSCRIPTION_NAME)
    public void receiveMessage(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        LOGGER.info("Message received: {}", message);
    }
}