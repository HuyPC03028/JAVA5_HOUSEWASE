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
	@GetMapping("/chiTietSanPham")
	public String chiTietSanPham() {
		return "chitietsanpham";
	}
}
