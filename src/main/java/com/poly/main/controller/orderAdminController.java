package com.poly.main.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.main.DAO.CartDAO;
import com.poly.main.DAO.OrderDAO;
import com.poly.main.DAO.OrderDetailDAO;
import com.poly.main.DAO.UserDAO;
import com.poly.main.model.Cart;
import com.poly.main.model.Order;
import com.poly.main.model.OrderDetail;
import com.poly.main.model.User;
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
	@Autowired
	CartDAO cartDao;
	@Autowired
	UserDAO userDao;
	
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
	@RequestMapping("/saveOrder")
	public String saveOrder(@RequestParam("address") String address) {
		int userId = sessionService.get("usernameId");
		
		System.out.println("id"+userId);
		List<Cart> cart = cartDao.findByUser(userId);
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (Cart car : cart) {
		    int quantity = car.getQuantity();
		    BigDecimal price = car.getProduct().getPrice();
		    BigDecimal cartTotalPrice = price.multiply(BigDecimal.valueOf(quantity));
		    totalPrice = totalPrice.add(cartTotalPrice);
		}
		User user = userDao.findById(userId).get();
		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(LocalDate.now());
		order.setTotalAmount(totalPrice);
		order.setNote("");
		dao.save(order);
		String str = order.getNote();
		OrderDetail orderdetail = new OrderDetail();
		int quantity=0;
		for (Cart cart2 : cart) {
			orderdetail.setProduct(cart2.getProduct());
			str +=", "+cart2.getProduct().getName()+"*"+cart2.getQuantity();
			order.setNote(str);
			dao.save(order);
			quantity+=cart2.getQuantity();
		}
		orderdetail.setAddress(address);
		orderdetail.setQuantity(quantity);
		orderdetail.setOrder(order);
		detailDao.save(orderdetail);
		cartDao.deleteAll(cart);
		return"redirect:/index";
	}
}
