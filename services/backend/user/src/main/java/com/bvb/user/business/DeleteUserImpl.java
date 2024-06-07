package com.bvb.user.business;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.bvb.user.persistence.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserImpl implements DeleteUserService {
    private final UserRepository userRepository;

    @Value("${spring.cloud.azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.servicebus.consumer.entity-name}")
    private String topicName;

    @Override
    public void deleteUser(long userId) {
        var user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        String userContextId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        if (Long.parseLong(userContextId) != userId) {
            throw new RuntimeException("User not authorized");
        }

        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();
        try {
            senderClient.sendMessage(new ServiceBusMessage(String.valueOf(userId)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }

        userRepository.deleteById(userId);
    }
}
