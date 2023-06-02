package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {

}
