package com.poly.main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.main.DAO.OrderDAO;
import com.poly.main.DAO.OrderDetailDAO;
import com.poly.main.model.OrderDetail;
import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

@Controller
public class orderAdminController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@Autowired
	OrderDAO dao;
	@Autowired
	OrderDetailDAO detailDao;
	
	@RequestMapping("admin/quanLyDonHang")
	public String quanLyOrder(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		
		OrderDetail order = new OrderDetail();
		model.addAttribute("order",order);
		
		String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<OrderDetail> orders = detailDao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("orders",orders);
		
	    
		return "admin/quanLyDonHang";
	}
}
