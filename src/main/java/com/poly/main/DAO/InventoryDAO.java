package com.poly.main.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.main.model.Inventory;

public interface InventoryDAO extends JpaRepository<Inventory, Integer> {
	@Query("SELECT o FROM Inventory o WHERE o.product.name LIKE ?1")
	Page<Inventory> findByKeywords(String keywords, Pageable pageable);
	@Query("SELECT o FROM Inventory o WHERE o.product.id LIKE ?1")
	Inventory findByProduct(int id);
}
