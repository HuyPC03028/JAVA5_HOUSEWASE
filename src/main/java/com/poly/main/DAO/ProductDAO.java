package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {

}
