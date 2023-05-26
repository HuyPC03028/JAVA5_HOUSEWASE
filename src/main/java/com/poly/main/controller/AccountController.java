package com.poly.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.main.model.User;
import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

import jakarta.validation.Valid;



@Controller
public class AccountController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	
	@GetMapping("/account/login")
	public String login(@ModelAttribute("user") User user) {
		return "login";
	}
	@PostMapping("/account/login")
	public String login(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		String un = paramService.getString("username", "");
		String pw = paramService.getString("password", "");
		boolean rm = paramService.getBoolean("remember", false);
		
		//Đăng nhập
		if (un.equals("minhhaou") && pw.equals("123456")) {
			sessionService.set("username", un);
			System.out.println("session: "+sessionService.get("username", ""));
			model.addAttribute("message", "Đăng nhập thành công");
			System.out.println("Đăng nhập thành công"+rm);
			if(rm==true) {
				cookieService.add("user", un, 10);
				System.out.println("c2"+cookieService.getValue("user", ""));
			}else {
				cookieService.remove("user");
			}
			return "redirect:/index";
		}else if (un.equals("admin") && pw.equals("123456")) {
			if(rm==true) {
				cookieService.add("admin", un, 10);
			}else {
				cookieService.remove("admin");
			}
			return "redirect:/admin";
		}
		model.addAttribute("message", "Sai Username hoặc password");
		if(result.hasErrors()){
			return "login";
        }
		return "login";
	}
	@RequestMapping("/logout")
	public String logout() {
		sessionService.remove("username");
		return "redirect:/index";
	}
	@GetMapping("/account/quenMK")
	public String QuenMK() {
		return "quenMK";
	}
	@GetMapping("/account/sign-up")
	public String signup(@ModelAttribute("user") User user) {
		return "sign-up";
	}
	@PostMapping("/account/sign-up")
	public String signup(@Valid @ModelAttribute("user") User user, BindingResult result) {
		if(result.hasErrors()){
			return "sign-up";
        }
		return "sign-up";
	}
	
	@ModelAttribute("admins")
	public Map<Boolean, String> getAdmins(){
	Map<Boolean, String> map = new HashMap<>();
	map.put(true, "Admin");
	map.put(false, "user");
	return map;
	}
}
