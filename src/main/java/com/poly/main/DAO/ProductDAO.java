package com.poly.main.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {

	Product findById(int id);

	List<Product> findAll();
}
