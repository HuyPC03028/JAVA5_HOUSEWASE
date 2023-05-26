package com.poly.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class mainController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@GetMapping("/index")
	public String index(Model model, HttpSession session) {
	    String username = sessionService.get("username", "");
        System.out.println("session: " + username);
		
		if (username!="") {
			model.addAttribute("loggedIn", true);
		}else {
			model.addAttribute("loggedIn", false);
		}
		
        model.addAttribute("username", username);
		return "index";
	}
	@GetMapping("/chiTietSanPham")
	public String chiTietSanPham() {
		return "chitietsanpham";
	}
	@GetMapping("/admin")
	public String admin() {
		return "admin/index";
	}
	@GetMapping("/admin/quanLyUser")
	public String quanLyUser() {
		return "admin/quanLyUser";
	}
	@GetMapping("admin/quanLyProduct")
	public String quanLyProduct() {
		return "admin/quanLyProduct";
	}
	@GetMapping("admin/quanLyKho")
	public String quanLyKho() {
		return "admin/quanLyKho";
	}
	@GetMapping("admin/quanLyDonHang")
	public String quanLyDonHang() {
		return "admin/quanLyDonHang";
	}
	@GetMapping("admin/quanLyKhuyenMai")
	public String quanLyKhuyenMai() {
		return "admin/quanLyKhuyenMai";
	}
	@GetMapping("admin/thongKe")
	public String thongKe() {
		return "admin/thongKe";
	}
}
