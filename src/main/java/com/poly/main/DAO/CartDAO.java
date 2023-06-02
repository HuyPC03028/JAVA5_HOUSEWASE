package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.Cart;

public interface CartDAO extends JpaRepository<Cart, Integer> {

}
