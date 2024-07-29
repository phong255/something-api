package dev.lhphong.somethingapi.Config.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProvider {
//    @Value(value = "${rabbitmq.queue.name}")
//    private String queue;
//
//    @Value(value = "${rabbitmq.exchange.name}")
//    private String exchange;
//
//    @Value(value = "${rabbitmq.routing.key}")
//    private String routingKey;
//
//    private final RabbitTemplate rabbitTemplate;
//
//    public RabbitMQProvider(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void sendMessage(String message){
//        rabbitTemplate.convertAndSend(exchange,routingKey,message);
//    }
}
