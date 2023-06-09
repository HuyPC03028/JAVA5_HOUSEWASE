package com.poly.main.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.main.model.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
	
}
