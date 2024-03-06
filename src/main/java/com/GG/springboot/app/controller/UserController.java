package com.GG.springboot.app.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.GG.springboot.app.entity.Role;
import com.GG.springboot.app.entity.Usuario;
import com.GG.springboot.app.service.IUserService;






@Controller
@RequestMapping("/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	public IUserService userService;
	
		
	BCryptPasswordEncoder passEncoder= new BCryptPasswordEncoder();

	// Para acceder al siguiente métod es con la url /user/register, siempre con el
	// puerto 8080
	@GetMapping("/register")

	public String createregister() {

		return "user/registro";
	}

	@PostMapping("/save")

	public String saveregister(Usuario user,RedirectAttributes flash) {

		logger.info("Usuario registro recibo de register:  {}", user);
		
		 // Crear una instancia de Role y agregar el rol "USER"
	    Role userRole = new Role();
	    userRole.setAuthority("ROLE_USER");

	    // Crear una lista para almacenar los roles del usuario y agregar el rol creado
	    List<Role> roles = new ArrayList<>();
	    roles.add(userRole);

	     
		user.setRoles(roles);
		user.setPassword(passEncoder.encode(user.getPassword()));
		user.setPassword(user.getPassword());
		user.setEmail(user.email);
		user.setEnabled("1");
		String mensajeFlash = (user.getUsername()!="") ? "User save successfully !"
				: "User save successfully !";
		flash.addFlashAttribute("success", mensajeFlash);
	
		logger.info("Usuario registro recibo de register y luego hago encoder de password:  {}", user);
		userService.saveUser(user);

		return "redirect:/";

	}



	

	        
	 



}
