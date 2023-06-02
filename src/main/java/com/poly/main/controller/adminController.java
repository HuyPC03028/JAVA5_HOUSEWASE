package com.poly.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.main.DAO.UserDAO;
import com.poly.main.model.User;
import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

import jakarta.validation.Valid;

@Controller
public class adminController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;

	@Autowired
	UserDAO dao;

	@GetMapping("/admin")
	public String admin() {
		return "admin/index";
	}
	
	/*------------quanLyUser Start----------------*/
	
	@RequestMapping("/admin/quanLyUser")
	public String quanLyUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		List<User> users = dao.findAll();
		model.addAttribute("users", users);	
		return "admin/quanLyUser";
	}

	@RequestMapping("/admin/editUser/{id}")
	public String editUser(Model model, @PathVariable("id") int id) {
	    User user = dao.findById(id).get();
	    model.addAttribute("user", user);
	    List<User> users = dao.findAll();
	    model.addAttribute("users", users);
	    model.addAttribute("activeTab", "tab2");
	    return "admin/quanLyUser";
	}

	@RequestMapping("/admin/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		dao.deleteById(id);
		return "redirect:/admin/quanLyUser";
	}

	@RequestMapping("/admin/saveUser")
	public String saveUser(@Valid User user, BindingResult result) {
		if(result.hasErrors()){
			return "redirect:/admin/quanLyUser#tab2";
        }
		dao.save(user);
		
		return "redirect:/admin/quanLyUser#tab1";
	}
	
	/*------------quanLyUser End----------------*/

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
