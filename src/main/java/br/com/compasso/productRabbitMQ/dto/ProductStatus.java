package br.com.compasso.productRabbitMQ.dto;

import br.com.compasso.productRabbitMQ.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductStatus {
	Product prodct;
	private String status;//progress,completed
	private String message;
	
}
