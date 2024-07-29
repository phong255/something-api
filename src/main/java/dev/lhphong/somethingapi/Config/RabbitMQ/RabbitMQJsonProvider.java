package dev.lhphong.somethingapi.Config.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQJsonProvider {
    @Value(value = "${rabbitmq.json.queue.name}")
    private String jsonQueue;

    @Value(value = "${rabbitmq.json.exchange.name}")
    private String jsonExchange;

    @Value(value = "${rabbitmq.json.routing.key}")
    private String jsonRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Object message){
        rabbitTemplate.convertAndSend(jsonExchange,jsonRoutingKey,message);
    }
}
