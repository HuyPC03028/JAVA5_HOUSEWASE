package com.poly.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.main.DAO.ProductDAO;
import com.poly.main.model.Product;
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
	
	@Autowired
	ProductDAO dao;

	
	@Autowired
    private ProductDAO productDAO;
	
	@GetMapping("/index")
	public String index(Model model, HttpSession session) {
	    String username = sessionService.get("username", "");
        System.out.println("session: " + username);
		List<Product> product = dao.findAll();
		if (username!="") {
			model.addAttribute("loggedIn", true);
		}else {
			model.addAttribute("loggedIn", false);
		}
		model.addAttribute("products", product);
        model.addAttribute("username", username);
		return "index";
	}
	

    @GetMapping("/chiTietSanPham")
    public String showProductDetail(Model model, @RequestParam("productId") int productId) {
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu dựa trên productId
        Product product = productDAO.findById(productId);
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
