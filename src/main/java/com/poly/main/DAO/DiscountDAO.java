package com.poly.main.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.main.model.Discount;
import com.poly.main.model.Product;


public interface DiscountDAO extends JpaRepository<Discount, Integer> {
	@Query("SELECT o FROM Discount o WHERE o.product.name LIKE ?1")
	Page<Discount> findByKeywords(String keywords, Pageable pageable);

	Discount findByProduct(Product product);
}
