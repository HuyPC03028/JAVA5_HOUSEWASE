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
<<<<<<< HEAD
	@GetMapping("/account/login")
	public String login() {
		return "login";
	}
	@GetMapping("/account/quenMK")
	public String QuenMK() {
		return "quenMK";
	}
	@GetMapping("/account/sign-up")
	public String signup() {
		return "sign-up";
	}
<<<<<<< Updated upstream
	@GetMapping("/mau")
	public String mau() {
		return "mau";
=======
<<<<<<< Updated upstream
=======
>>>>>>> 8f110c14af56d72820c223516c5c470c7bac70e0
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
<<<<<<< HEAD

=======
>>>>>>> Stashed changes
>>>>>>> Stashed changes
=======
	@GetMapping("admin/thongKe")
	public String thongKe() {
		return "admin/thongKe";
>>>>>>> 8f110c14af56d72820c223516c5c470c7bac70e0
	}
}

