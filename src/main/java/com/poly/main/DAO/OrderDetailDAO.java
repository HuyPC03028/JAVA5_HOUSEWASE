package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

}
