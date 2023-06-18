package com.poly.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.main.DAO.UserDAO;
import com.poly.main.model.MailInfo;
import com.poly.main.model.User;
import com.poly.main.service.CookieService;
import com.poly.main.service.MailerServiceImpl;
import com.poly.main.service.ParamService;
import com.poly.main.service.SessionService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;



@Controller
public class AccountController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@Autowired
	UserDAO dao;
	@Autowired
	MailerServiceImpl impl;
	
	@GetMapping("/account/login")
	public String login(@ModelAttribute("user") User user, Model model) {
		String Message = sessionService.get("Message");
	    if (Message != null) {
	        model.addAttribute("Message", Message);
	        sessionService.remove("Message");
	    }
		return "login";
	}
	
	@PostMapping("/account/login")
	public String login(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		User user3 = new User();
		boolean rm = paramService.getBoolean("remember", false);
		boolean check = false;
		List<User> listuser = dao.findAll();
		for (User user2 : listuser) {
			if(user2.getUsername().equals(user.getUsername())){
				if (user2.isActive()==false) {
					sessionService.set("Message", "Tài khoản bị vô hiệu hóa!!!");
					return "redirect:/account/login";
				}else {
					if(user2.getPassword().equals(user.getPassword())) {
						check = true;
						sessionService.set("userSession", user2);
						user3 = user2;
						sessionService.set("username", user.getUsername());
						sessionService.set("usernameId", user2.getId());
						if(rm) {
							cookieService.add("username", user.getUsername(),10);
							cookieService.add("password", user.getPassword(),10);
						}else {
							cookieService.remove("username");
							cookieService.remove("password");
						}
					}else {
						sessionService.set("Message", "Tên tài khoản hoặc mật khẩu sai!!!");
						return "redirect:/account/login";
					}
				}
				
			}
		}
		
		
		if(check == true && user3.isAdmin()) {
			return "redirect:/admin";
		}
			
			return "redirect:/index";
		
		
	}
	@RequestMapping("/logout")
	public String logout() {
		sessionService.remove("username");
		sessionService.remove("usernameId");
		sessionService.remove("userSession");
		return "redirect:/index";
	}
	
	public String generateRandomString() {
        int length = 10; // Độ dài của chuỗi ký tự ngẫu nhiên
        String characters = "ABCDEF123456";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
	
	@GetMapping("/account/quenMK")
	public String QuenMK(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		String Message = sessionService.get("Message");
	    if (Message != null) {
	        model.addAttribute("Message", Message);
	        sessionService.remove("Message");
	    }
	    
		return "quenMK";
	}
	
	@PostMapping("/account/quenMK")
	public String post_forgot(Model model, @ModelAttribute("user") User user,@RequestParam("email")String recipient) {
		
		boolean check = false;
		List<User> list = dao.findAll();
		for (User nguoiDung : list) {
			if(nguoiDung.getEmail().equals(user.getEmail())) {
				check = true;
				
			}
		}
		
		if(check == true) {
			String newPassword = generateRandomString();
			sessionService.set("Email", user.getEmail());
			sessionService.set("MXN", newPassword);
//			User customer = dao.findByEmail(user.getEmail());
//			customer.setPassword(newPassword);
//			dao.save(customer);
			
			String subject = "Đổi mật khẩu";
			String message = newPassword;
			
			MailInfo mail = new MailInfo(recipient, subject, message);
			try {
				impl.send(mail);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sessionService.set("Message", "Mã xác nhận đã được gửi về mail của bạn!!!");
			return "redirect:/account/xacNhanMK";
		}else {
		sessionService.set("Message", "Không tìm thấy Email!!!");
		return "redirect:/account/quenMK";
	}}
	@GetMapping("/account/xacNhanMK")
	public String xacNhanMK(Model model) {
		String Message = sessionService.get("Message");
	    if (Message != null) {
	        model.addAttribute("Message", Message);
	        sessionService.remove("Message");
	    }
		return "xacNhanMK";
	}
	@PostMapping("/account/xacNhanMK")
	public String xacNhanMK(Model model, @ModelAttribute("user") User user, @RequestParam("MXN") String mxn, @RequestParam("nhapLai") String nhapLai) {
		String email = sessionService.get("Email");
		String MXN = sessionService.get("MXN");
		if (nhapLai.equals(user.getPassword())) {
			if (MXN.equals(mxn)) {
				User customer = dao.findByEmail(email);
				customer.setPassword(user.getPassword());
				dao.save(customer);
				sessionService.remove("MXN");
				sessionService.remove("Email");
			}else {
				sessionService.set("Message", "Mã xác nhận sai!!!");
				return "redirect:/account/xacNhanMK";
			}
		}else {
			sessionService.set("Message", "Mật khẩu không trùng khớp!!!");
			return "redirect:/account/xacNhanMK";
		}
		sessionService.set("Message", "Đổi mật khẩu thành công!!!");
		return "redirect:/account/login";
	}
	@GetMapping("/account/xacNhanMail")
	public String xacNhanMail(Model model) {
		String Message = sessionService.get("Message");
	    if (Message != null) {
	        model.addAttribute("Message", Message);
	        sessionService.remove("Message");
	    }
		return "xacNhanMail";
	}
	@PostMapping("/account/xacNhanMail")
	public String xacNhanMail(Model model,@RequestParam("MXN") String mxn) {
		String maXacNhan = sessionService.get("maXacNhan");
		int id = sessionService.get("UserXN");
		if (mxn.equals(maXacNhan)) {
			User user = dao.findById(id).get();
			user.setActive(true);
			dao.save(user);
			sessionService.remove("maXacNhan");
			sessionService.remove("UserXN");
			sessionService.set("Message", "Đăng ký thành công!!!");
			return "redirect:/account/login";
		}
		sessionService.set("Message", "Mã xác nhận không chính xác!!!");
		return "redirect:/account/xacNhanMail";
	}

	@GetMapping("/account/sign-up")
	public String signup(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		
		String Message = sessionService.get("Message");
	    if (Message != null) {
	        model.addAttribute("Message", Message);
	        sessionService.remove("Message");
	    }
	    
		return "sign-up";
	}
	@PostMapping("/account/sign-up")
	public String signup(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,@RequestParam("email")String recipient) {
		if(result.hasErrors()){
			return "sign-up";
        }

		List<User> listUser = dao.findAll();
		boolean check = false;
		for (User nguoiDung : listUser) {
			if(nguoiDung.getUsername().equals(user.getUsername())) {
				sessionService.set("Message", "Username đã tồn tại!!!");
				check = false;
				break;
			}else if(nguoiDung.getEmail().equals(user.getEmail())){
				sessionService.set("Message", "Email đã tồn tại!!!");
				check = false;
				break;
			}else {
				
				check = true;
			}
		}	
		
		if(check==true) {
			user.setAdmin(false);
			user.setActive(false);
			dao.save(user);
			
			//tạo mail mã xac nhận
			String maXacNhan = generateRandomString();
			
			String subject = "Đổi mật khẩu";
			String message = "Mã xác nhận của bạn: "+maXacNhan;
			
			MailInfo mail = new MailInfo(recipient, subject, message);
			try {
				impl.send(mail);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sessionService.set("UserXN", user.getId());
			sessionService.set("maXacNhan", maXacNhan);
			sessionService.set("Message", "Mã xác nhận đã được gửi về mail của bạn!!!");
			
			return "redirect:/account/xacNhanMail";
		}else {
			return "redirect:/account/sign-up";
		}
		
	}
	
	@ModelAttribute("admins")
	public Map<Boolean, String> getAdmins(){
	Map<Boolean, String> map = new HashMap<>();
	map.put(true, "Admin");
	map.put(false, "user");
	return map;
	}
}
