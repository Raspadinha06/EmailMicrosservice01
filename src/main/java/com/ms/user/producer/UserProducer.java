package com.ms.user.producer;

import com.ms.user.domain.User;
import com.ms.user.dtos.EmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;
    public UserProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(User user){
        var emailDTO = new EmailDTO();
        emailDTO.setUserId(user.getUserId());
        emailDTO.setEmailTo(user.getEmail());
        emailDTO.setSubject("Successful registration!");
        emailDTO.setText(user.getName() + ", welcome! \nThanks for signing up, enjoy all the features of our platform now!");

        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
    }

}
