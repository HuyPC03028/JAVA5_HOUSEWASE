package com.poly.main.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.poly.main.service.SessionService;


@Controller
public class ThongKeController {
	@Autowired
	SessionService sessionService;
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
    	
    	LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
		 List<OrderDetail> orderDetails = dao.findByOrderOrderDateBetween(startDateTime, endDateTime);
	     model.addAttribute("orderDetails", orderDetails);
	     BigDecimal totalPrice = BigDecimal.ZERO;
	     for (OrderDetail car : orderDetails) {
	         BigDecimal price = car.getOrder().getTotalAmount();
	         totalPrice = totalPrice.add(price);
	     }

	     model.addAttribute("totalPrice", totalPrice);
	     
	    	if (startDate!=null&&endDate!=null) {
				model.addAttribute("startDate",startDate);
				model.addAttribute("endDate",endDate);
			}
		return "admin/thongKe";
	}
}
