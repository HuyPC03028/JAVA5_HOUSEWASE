package com.poly.main.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.poly.main.DAO.CategoryDAO;
import com.poly.main.DAO.ProductDAO;
import com.poly.main.model.Category;
import com.poly.main.model.Product;
import com.poly.main.model.User;
import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;


import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
public class productAdminController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@Autowired
	ProductDAO dao;
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	ServletContext app;
	
	@RequestMapping("admin/quanLyProduct")
	public String quanLyProduct(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		
		String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		
		Product product = new Product();
		model.addAttribute("product",product);
		
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> products = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("products",products);
		
		//Gọi tất cả category
		List<Category> categories = categoryDAO.findAll();
	    model.addAttribute("categories", categories);
	    
		return "admin/quanLyProduct";
	}
	
	@RequestMapping("/admin/editProduct/{id}")
	public String editProduct(Model model, @PathVariable("id") int id, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		//tìm sản phẩm theo id
	    Product product = dao.findById(id).get();
	    model.addAttribute("product", product);
	    
	    String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));

		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> products = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("products",products);
		
	    //Gọi tất cả category
		List<Category> categories = categoryDAO.findAll();
		model.addAttribute("categories", categories);
		
	    return "admin/quanLyProduct";
	}
	
	@RequestMapping("/admin/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		dao.deleteById(id);
		return "redirect:/admin/quanLyProduct";
	}
	@RequestMapping("/admin/saveProduct")
	public String saveProduct(@Valid Product product, BindingResult result, Model model,@RequestParam("image") MultipartFile image, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) throws IllegalStateException, IOException {
		//upload file
		if (!image.isEmpty()) {
	            String filename = image.getOriginalFilename();
	            File file = new File(app.getRealPath("/image/" + filename));
	            image.transferTo(file);
	            System.out.println(filename);
	            product.setImage(filename);
	            model.addAttribute("hasMessage", false);
		}else {
			model.addAttribute("hasMessage", true);
			model.addAttribute("message", "Cập nhật thất bại!");
	        System.out.println(result.toString());
	        

		    String kwords = kw.orElse(sessionService.get("keywords", ""));
			sessionService.set("keywords", kwords);
			model.addAttribute("keywords", sessionService.get("keywords", ""));

			Pageable pageable = PageRequest.of(p.orElse(0), 5);
			Page<Product> products = dao.findByKeywords("%" + kwords + "%", pageable);
			model.addAttribute("products",products);
		    
		    //Gọi tất cả category
		    List<Category> categories = categoryDAO.findAll();
		    model.addAttribute("categories", categories);
		    
	        return "admin/quanLyProduct";
		}
		//validate
			 
		// Kiểm tra nếu không chọn category
	    if (product.getCategory().getId() == 0) {
	    	result.rejectValue("category.id", "NotEmpty.Product.category");
	    }
	    if (result.getErrorCount()>1) {
	        model.addAttribute("message", "Cập nhật thất bại!");
	        System.out.println(result.toString());
	        

		    String kwords = kw.orElse(sessionService.get("keywords", ""));
			sessionService.set("keywords", kwords);
			model.addAttribute("keywords", sessionService.get("keywords", ""));

			Pageable pageable = PageRequest.of(p.orElse(0), 5);
			Page<Product> products = dao.findByKeywords("%" + kwords + "%", pageable);
			model.addAttribute("products",products);
		    
		    //Gọi tất cả category
		    List<Category> categories = categoryDAO.findAll();
		    model.addAttribute("categories", categories);
		    
	        return "admin/quanLyProduct";
	    }
	    
	    dao.save(product);
	    return "redirect:/admin/editProduct/" + product.getId();
	}
	@RequestMapping("/admin/clearProduct")
	public String clearProduct() {
		return "redirect:/admin/quanLyProduct";
	}
		
}
