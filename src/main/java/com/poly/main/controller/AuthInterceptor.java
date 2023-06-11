package com.poly.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poly.main.model.User;
import com.poly.main.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	SessionService session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = session.get("userSession"); // lấy từ session
		System.out.println(user);
		
		if (user == null) {
			response.sendRedirect("/account/login");
			return false;
		} else if (user.isAdmin() == false) {
			response.sendRedirect("/account/login");
			return false;
		} else if (user.isAdmin()) {
			return true;
		}
		return true;
	}
	
	
}