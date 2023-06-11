package com.poly.main.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.main.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	@Query("SELECT o FROM User o WHERE o.fullname LIKE ?1")
	Page<User> findByKeywords(String keywords, Pageable pageable);
User findByUsername(String tenDangNhap);
	
	User findByEmail(String email);
}
