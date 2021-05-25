package br.com.compasso.productRabbitMQ.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.compasso.productRabbitMQ.config.MessagingConfig;
import br.com.compasso.productRabbitMQ.model.Product;

@Component
public class ProductPublisher {
	@Autowired
	private RabbitTemplate template;
	
	public void send(Product prod) {
		template.convertAndSend(MessagingConfig.EXCHANGE,MessagingConfig.ROUTING_KEY, prod);
	}
	
}
