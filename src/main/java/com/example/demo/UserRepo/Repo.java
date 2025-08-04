package com.example.demo.UserRepo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Product;

@Repository
public interface Repo extends JpaRepository<Product, Integer> {

	List<Product> findByCategory(String category);

	

	
}
