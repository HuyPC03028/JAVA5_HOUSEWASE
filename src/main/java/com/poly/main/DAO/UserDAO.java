package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poly.main.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	
}
