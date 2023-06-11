package com.poly.main.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.main.DAO.OrderDetailDAO;
import com.poly.main.model.OrderDetail;


@Controller
public class ThongKeController {
	
    @Autowired
    OrderDetailDAO dao;
    @GetMapping("/admin/thongKe")
    public String thongKe() {
		return "admin/thongKe";
	}
    @PostMapping("/admin/thongKe")
    public String thongKe(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
		 List<OrderDetail> orderDetails = dao.findByOrderOrderDateBetween(startDate, endDate);
	     model.addAttribute("orderDetails", orderDetails);
	     BigDecimal totalPrice = BigDecimal.ZERO;
	     for (OrderDetail car : orderDetails) {
	         BigDecimal price = car.getOrder().getTotalAmount();
	         totalPrice = totalPrice.add(price);
	     }

	     model.addAttribute("totalPrice", totalPrice);
		return "admin/thongKe";
	}
}
