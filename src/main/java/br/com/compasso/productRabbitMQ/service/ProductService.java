package br.com.compasso.productRabbitMQ.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compasso.productRabbitMQ.dto.ProductDto;
import br.com.compasso.productRabbitMQ.model.Product;
import br.com.compasso.productRabbitMQ.publisher.ProductPublisher;
import br.com.compasso.productRabbitMQ.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductPublisher publisher;
	
	@Autowired
	ProductRepository repository;
	
	public List<ProductDto> findAll(){
		List<Product> prod = repository.findAll();
		return ProductDto.converter(prod);
		
	}
	
	public Optional<Product> findById(Long id){
		Optional<Product> prod = repository.findById(id);
		return prod;
	}
	public List<Product> findByMinMaxPrice(double Min_price, double Max_price){
		List<Product> prod = repository.findByMinMaxPrice(Min_price, Max_price);
		return prod;
		
	}
	
	public List<Product> findBySearch(Double min_price, Double max_price, String name, String description) {
		List<Product> prod = repository.findBySearch(min_price, max_price, name, description);
		return prod;
	}
	public void save(Product prod) {
		publisher.send(prod);
		repository.save(prod);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}
}
