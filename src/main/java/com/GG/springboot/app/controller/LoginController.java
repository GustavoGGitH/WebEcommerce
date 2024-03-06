package com.GG.springboot.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	
	@GetMapping ("/login")
	public String Login(@RequestParam (value="error",required=false) String error, Model model, Principal principal, RedirectAttributes flash,
						@RequestParam (value="logout",required=false) String logout) {
		
		if(principal != null) {
			flash.addFlashAttribute("info","You have already logged in");
			return "redirect:/";
		
		}
		if (error !=null) {
			model.addAttribute("error", "Login error, incorrect username or password");
			
		}
		
		if (logout !=null) {
			model.addAttribute("success", "you logeed out successfully");
			
		}
		
		
		return "login";
	}

}
