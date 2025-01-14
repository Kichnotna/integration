package org.example.integration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {
    private final UserRepository userRepository;

    public UserListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveUser(UserDto userDto) {
        try {
            var user = new User();
            user.setName(userDto.getName());
            user.setDescription(userDto.getDescription());

            userRepository.save(user);
            System.out.println("Received user: " + userDto);
        } catch (Exception e) {
            System.out.println("Error receiving user: " + userDto);
        }

    }
}
