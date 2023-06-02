package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

}
