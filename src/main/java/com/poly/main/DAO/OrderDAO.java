package com.poly.main.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.main.model.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	

}
