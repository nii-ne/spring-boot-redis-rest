package com.example.springredis.consumer;

import com.example.springredis.entities.User;
import com.example.springredis.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "${rabbitmq.user-queue-name}", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(String message) {
        try{
            System.err.println("QUEUE: "+message);
            //User user = objectMapper.readValue(message, User.class);
            //userService.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
