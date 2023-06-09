package com.poly.main.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.main.DAO.DiscountDAO;
import com.poly.main.DAO.ProductDAO;
import com.poly.main.model.Category;
import com.poly.main.model.Discount;
import com.poly.main.model.Product;
import com.poly.main.model.User;
import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

import jakarta.validation.Valid;

@Controller
public class discountAdminController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@Autowired
	DiscountDAO dao;
	@Autowired
	ProductDAO productDao;
	
	@RequestMapping("admin/quanLyKhuyenMai")
	public String quanLyKhuyenMai(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		
		Discount discount = new Discount();
		model.addAttribute("discount",discount);
		
		String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Discount> discounts = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("discounts",discounts);
		
		//Gọi tất cả sản phẩm
		List<Product> products = productDao.findAll();
	    model.addAttribute("products", products);
	    
		return "admin/quanLyKhuyenMai";
	}
	
	@RequestMapping("/admin/editDiscount/{id}")
	public String editDiscount(Model model, @PathVariable("id") int id, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		//tìm sản phẩm discount theo id
		Discount discount = dao.findById(id).get();
	    model.addAttribute("discount", discount);
	    
	    String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Discount> discounts = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("discounts",discounts);
	  		
	  	//Gọi tất cả sản phẩm
	  	List<Product> products = productDao.findAll();
	  	model.addAttribute("products", products);
		
	    return "admin/quanLyKhuyenMai";
	}
	
	@RequestMapping("/admin/deleteDiscount/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		dao.deleteById(id);
		return "redirect:/admin/quanLyKhuyenMai";
	}
	
	@RequestMapping("/admin/saveDiscount")
	public String saveDiscount(@Valid Discount discount, BindingResult result, Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p){
		
		//validate
		if (discount.getProduct().getId() == 0) {
	    	result.rejectValue("product.id", "NotEmpty.Discount.product");
	    }
	    if (result.hasErrors()) {
	        model.addAttribute("message", "Cập nhật thất bại!");
	        System.out.println(result.toString());
	        
	        String kwords = kw.orElse(sessionService.get("keywords", ""));
			sessionService.set("keywords", kwords);
			model.addAttribute("keywords", sessionService.get("keywords", ""));
			
			Pageable pageable = PageRequest.of(p.orElse(0), 5);
			Page<Discount> discounts = dao.findByKeywords("%" + kwords + "%", pageable);
			model.addAttribute("discounts",discounts);
		  		
		  	//Gọi tất cả sản phẩm
		  	List<Product> products = productDao.findAll();
		  	model.addAttribute("products", products);
		    
	        return "admin/quanLyKhuyenMai";
	    }
	    
	    dao.save(discount);
	    return "redirect:/admin/editDiscount/" + discount.getId();
	}
	@RequestMapping("/admin/clearDiscount")
	public String clearDiscount() {
		return "redirect:/admin/quanLyKhuyenMai";
	}
}
