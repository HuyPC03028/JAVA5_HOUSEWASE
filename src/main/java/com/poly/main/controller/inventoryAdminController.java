package com.poly.main.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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

import com.poly.main.DAO.InventoryDAO;
import com.poly.main.DAO.ProductDAO;
import com.poly.main.model.Category;
import com.poly.main.model.Inventory;
import com.poly.main.model.Product;
import com.poly.main.model.User;
import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

import jakarta.validation.Valid;

@Controller
public class inventoryAdminController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@Autowired
	InventoryDAO dao;
	@Autowired
	ProductDAO productDao;
	
	@RequestMapping("/admin/quanLyKho")
	public String quanLyKho(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		
		Inventory inventory = new Inventory();
		model.addAttribute("inventory",inventory);
		
		String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Inventory> inventorys = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("inventorys",inventorys);
		
		//Gọi tất cả Sản phẩm
		List<Product> products = productDao.findAll();
	    model.addAttribute("products", products);
	    
		return "admin/quanLyKho";
	}
	@RequestMapping("/admin/editKho/{id}")
	public String editKho(Model model, @PathVariable("id") int id, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		//tìm sản phẩm theo id
		Inventory inventory = dao.findById(id).get();
	    model.addAttribute("inventory", inventory);
	    
	    String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Inventory> inventorys = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("inventorys",inventorys);
	  
	    
	  	//Gọi tất cả Sản phẩm
	  	List<Product> products = productDao.findAll();
	  	model.addAttribute("products", products);
		
	    return "admin/quanLyKho";
	}
	
	@RequestMapping("/admin/deleteKho/{id}")
	public String deleteKho(@PathVariable("id") int id) {
		dao.deleteById(id);
		return "redirect:/admin/quanLyKho";
	}
	@RequestMapping("/admin/saveKho")
	public String saveKho(@Valid Inventory inventory, BindingResult result, Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		if (inventory.getDate() == null) {
		    inventory.setDate(LocalDate.now()); // Gán giá trị ngày hiện tại
		}
		//validate
		// Kiểm tra nếu không chọn category
	    if (inventory.getProduct().getId() == 0) {
	    	result.rejectValue("product.id", "NotEmpty.Inventory.product");
	    }
	    if (result.hasErrors()) {
	        model.addAttribute("message", "Cập nhật thất bại!");
	        System.out.println(result.toString());
	        
	        String kwords = kw.orElse(sessionService.get("keywords", ""));
			sessionService.set("keywords", kwords);
			model.addAttribute("keywords", sessionService.get("keywords", ""));
			Pageable pageable = PageRequest.of(p.orElse(0), 5);
			Page<Inventory> inventorys = dao.findByKeywords("%" + kwords + "%", pageable);
			model.addAttribute("inventorys",inventorys);
		    
			//Gọi tất cả Sản phẩm
		  	List<Product> products = productDao.findAll();
		  	model.addAttribute("products", products);
			
		    
	        return "admin/quanLyKho";
	    }
	    
	    dao.save(inventory);
	    return "redirect:/admin/editKho/" + inventory.getId();
	}
	@RequestMapping("/admin/clearKho")
	public String clearKho() {
		return "redirect:/admin/quanLyKho";
	}
}
