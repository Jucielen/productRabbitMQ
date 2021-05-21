package br.com.compasso.productRabbitMQ.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.productRabbitMQ.config.MessagingConfig;
import br.com.compasso.productRabbitMQ.dto.OrderStatus;
import br.com.compasso.productRabbitMQ.dto.ProductDto;
import br.com.compasso.productRabbitMQ.dto.ProductStatus;
import br.com.compasso.productRabbitMQ.form.ProductForm;
import br.com.compasso.productRabbitMQ.model.MensagensErro;
import br.com.compasso.productRabbitMQ.model.Product;

@RestController

public class ProductController {

	@Autowired
	private RabbitTemplate template;

	@PostMapping("/products")
	public ResponseEntity<?> newProduct(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder,
			BindingResult result, Product prod) {
		if (result.hasErrors()) {
			System.out.println("Erro");
			return ResponseEntity.status(400).body(new MensagensErro(400, "Verifique as informações informadas"));
		}

		prod.setId(UUID.randomUUID().toString());
		prod.setName(form.getName());
		prod.setDescription(form.getDescription());
		prod.setPrice(Double.valueOf(form.getPrice()));

		// OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed
		// succesfully in " + restaurantName);
		// template.convertAndSend(MessagingConfig.EXCHANGE,
		// MessagingConfig.ROUTING_KEY, orderStatus);

		ProductStatus productStatus = new ProductStatus(prod, "PROCESS",
				"order placed succesfully in " + prod.getName());

		template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, productStatus);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(prod.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProductDto(prod));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> onError() {
		return ResponseEntity.status(400).body(new MensagensErro(400, "Verifique as informações informadas"));
	}
}
