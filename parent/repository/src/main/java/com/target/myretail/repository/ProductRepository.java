package com.target.myretail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	
	public Optional<Product> findById(Integer id);
	
	public List<Product> findByPriceBetween(Double lowPrice, Double highPrice);
}

