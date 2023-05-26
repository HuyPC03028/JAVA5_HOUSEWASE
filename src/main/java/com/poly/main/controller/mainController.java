package com.poly.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {
	@GetMapping("/index")
	public String index() {
		return "index";
	}
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

=======
>>>>>>> Stashed changes
>>>>>>> Stashed changes
	}
}

