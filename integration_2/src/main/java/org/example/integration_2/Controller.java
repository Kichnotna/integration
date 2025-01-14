package org.example.integration_2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controller {
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    public Controller(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public void addUser(@RequestBody UserDto userDto) {
        try {
            var user = new User();
            user.setName(userDto.getName());
            user.setDescription(userDto.getDescription());
            user.setAge(userDto.getAge());

            userRepository.save(user);
            rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, userDto);
            System.out.println("Sent user: " + userDto.getName());
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
