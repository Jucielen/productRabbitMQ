package br.com.compasso.productRabbitMQ.consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.compasso.productRabbitMQ.config.MessagingConfig;
import br.com.compasso.productRabbitMQ.model.Product;
import br.com.compasso.productRabbitMQ.repository.ProductRepository;

@Component
public class ProductConsumer {
	
	@Autowired
	private ProductRepository repository;
	
    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(Product prod) {
    	prod.setName(prod.getName()+" RabbitMQ");
    	repository.save(prod);
        System.out.println("Message recieved from queue : " + prod);
    }
}
