package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.Discount;

public interface DiscountDAO extends JpaRepository<Discount, Integer> {

}
