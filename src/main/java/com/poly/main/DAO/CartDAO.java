package com.poly.main.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.main.model.Cart;

public interface CartDAO extends JpaRepository<Cart, Integer> {
	 @Query("SELECT c FROM Cart c WHERE c.user.username = :username")
	   List<Cart> findByUserUsername(String username);
}
