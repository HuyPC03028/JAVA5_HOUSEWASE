package com.poly.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.main.DAO.UserDAO;
import com.poly.main.model.Product;
import com.poly.main.model.User;
import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

import jakarta.validation.Valid;

@Controller
public class userAdminController {
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
	public String quanLyUser(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		
		User user = new User();
		model.addAttribute("user", user);
		
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<User> users = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("users", users);
		
		return "admin/quanLyUser";
	}	

	@RequestMapping("/admin/editUser/{id}")
	public String editUser(Model model, @PathVariable("id") int id, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
	    User user = dao.findById(id).get();
	    model.addAttribute("user", user);
	    String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<User> users = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("users", users);
	    return "admin/quanLyUser";
	}

	@RequestMapping("/admin/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		dao.deleteById(id);
		return "redirect:/admin/quanLyUser";
	}

	@RequestMapping("/admin/saveUser")
	public String saveUser(@Valid User user, BindingResult result, Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		String id = paramService.getString("id", "");
		String uid = String.valueOf(user.getId());
		System.out.println(id);
		System.out.println(uid);
		if (uid.equals(id)) {
			sessionService.set("error", "ID đã tồn tại!!!");
		}
		if(result.hasErrors()){
			model.addAttribute("message", "Cập nhật thất bại!");
			 String kwords = kw.orElse(sessionService.get("keywords", ""));
				sessionService.set("keywords", kwords);
				model.addAttribute("keywords", sessionService.get("keywords", ""));
				Pageable pageable = PageRequest.of(p.orElse(0), 5);
				Page<User> users = dao.findByKeywords("%" + kwords + "%", pageable);
				model.addAttribute("users", users);
			return "admin/quanLyUser";
        }
		dao.save(user);
		
		return "redirect:/admin/editUser/" + user.getId();
	}
	
	/*------------quanLyUser End----------------*/


	
}
