package com.poly.main.DAO;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.main.model.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {

	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1")
	Page<Product> findByKeywords(String keywords, Pageable pageable);
	
	 Page<Product> findAll(Pageable pageable);
	 
	 @Query("SELECT o FROM Product o WHERE o.category.id LIKE ?1")
	 List<Product> findByCategory(int categoryId); 
}
