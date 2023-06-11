package com.poly.main.controller;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

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
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.main.DAO.CartDAO;
import com.poly.main.DAO.ProductDAO;
import com.poly.main.DAO.UserDAO;
import com.poly.main.model.Cart;
import com.poly.main.model.Category;
import com.poly.main.model.Product;
import com.poly.main.model.User;

import com.poly.main.service.CookieService;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class mainController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;

	@Autowired
	CartDAO cartDao;
	@Autowired
	UserDAO userDao;
	@Autowired
	ProductDAO productDao;

	
	@Autowired
	ProductDAO dao;

	
	@Autowired
    private ProductDAO productDAO;
	

	@GetMapping("/index")
	public String index(Model model, HttpSession session,@RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
	    String username = sessionService.get("username", "");
        System.out.println("session: " + username);
		
		if (username!="") {
			model.addAttribute("loggedIn", true);
		}else {
			model.addAttribute("loggedIn", false);
		}
		
		String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.set("keywords", kwords);
		model.addAttribute("keywords", sessionService.get("keywords", ""));
		
		Pageable pageable = PageRequest.of(p.orElse(0), 12);
		Page<Product> products = dao.findByKeywords("%" + kwords + "%", pageable);
		model.addAttribute("products",products);
		
		List<Product> dogiadung = dao.findByCategory(1);
		model.addAttribute("dogiadung",dogiadung);
		
		List<Product> tuLanh = dao.findByCategory(2);
		model.addAttribute("tulanh",tuLanh);
		
		List<Product> bepdien = dao.findByCategory(3);
		model.addAttribute("bepdien",bepdien);
		
		List<Product> maysaychen = dao.findByCategory(1003);
		model.addAttribute("maysaychen",maysaychen);
		
		List<Product> bepga = dao.findByCategory(4);
		model.addAttribute("bepga",bepga);
				
		List<Product> ghe = dao.findByCategory(1004);
		model.addAttribute("ghe",ghe);
		
        model.addAttribute("username", username);
        
        Cart cart = new Cart();
		model.addAttribute("cart",cart);
		
		List<Cart> carts = cartDao.findByUserUsername(username);
		model.addAttribute("carts",carts);
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (Cart car : carts) {
		    int quantity = car.getQuantity();
		    BigDecimal price = car.getProduct().getPrice();
		    BigDecimal cartTotalPrice = price.multiply(BigDecimal.valueOf(quantity));
		    totalPrice = totalPrice.add(cartTotalPrice);
		    
		}

		model.addAttribute("totalPrice", totalPrice);
		
		
		return "index";
	}

	@RequestMapping("/admin/deleteCart/{id}")
	public String deleteCart(@PathVariable("id") int id) {
		cartDao.deleteById(id);
		return "redirect:/index";
	}
	

    @GetMapping("/chiTietSanPham")
    public String showProductDetail(Model model, @RequestParam("productId") int productId) {
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu dựa trên productId
        Product product = productDAO.findById(productId).get();
        System.out.println(productId);
        String username = sessionService.get("username", "");
        if (username!="") {
			model.addAttribute("loggedIn", true);
		}else {
			model.addAttribute("loggedIn", false);
		}
        model.addAttribute("username", username);
        List<Product> product1 = dao.findAll();
        model.addAttribute("product", product);
        model.addAttribute("products", product1);
        return "chitietsanpham"; // Trả về tên của template hiển thị trang chi tiết sản phẩm
    }	

}
