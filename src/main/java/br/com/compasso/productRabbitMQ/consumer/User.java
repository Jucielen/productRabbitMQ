package br.com.compasso.productRabbitMQ.consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.compasso.productRabbitMQ.config.MessagingConfig;
import br.com.compasso.productRabbitMQ.dto.ProductStatus;

@Component
public class User {

    @RabbitListener(queues = MessagingConfig.QUEUE)
    /*public void consumeMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message recieved from queue : " + orderStatus);
    }*/
    public void consumeMessageFromQueue(ProductStatus productStatus) {
        System.out.println("Message recieved from queue : " + productStatus);
    }
}
