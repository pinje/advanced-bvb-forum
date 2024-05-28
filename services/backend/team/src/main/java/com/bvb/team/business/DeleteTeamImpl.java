package com.bvb.team.business;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.bvb.team.business.exception.TeamDoesntExistsException;
import com.bvb.team.persistence.Team;
import com.bvb.team.persistence.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTeamImpl implements DeleteTeamService {

    @Value("${spring.cloud.azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.servicebus.consumer.entity-name}")
    private String topicName;

    private final TeamRepository teamRepository;
    private final MediaService mediaService;

    @Override
    public void deleteTeam(long teamId) {
        Team team = teamRepository.findById(teamId).stream().findFirst().orElse(null);

        if (team == null) {
            throw new TeamDoesntExistsException();
        }

        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();
        try {
            senderClient.sendMessage(new ServiceBusMessage(String.valueOf(teamId)).setSubject("team-id"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }

        teamRepository.deleteById(teamId);

        try {
            mediaService.deleteImage(team.getLogoId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }
}
