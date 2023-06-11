package com.poly.main.DAO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.main.model.Category;
import com.poly.main.model.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {
	@Query("SELECT o FROM OrderDetail o WHERE o.order.user.fullname LIKE ?1")
	Page<OrderDetail> findByKeywords(String keywords, Pageable pageable);
	List<OrderDetail> findByOrderOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
