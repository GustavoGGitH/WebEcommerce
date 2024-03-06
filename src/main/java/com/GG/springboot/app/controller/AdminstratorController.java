package com.GG.springboot.app.controller;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.GG.springboot.app.entity.Categorie;
import com.GG.springboot.app.entity.Order;
import com.GG.springboot.app.entity.Usuario;
import com.GG.springboot.app.paginator.PageRender;
import com.GG.springboot.app.service.CategorieServiceImpl;
import com.GG.springboot.app.service.IOrderService;
import com.GG.springboot.app.service.IProductService;
import com.GG.springboot.app.service.IUserService;





@Controller
@RequestMapping("/administrator")

public class AdminstratorController {
	
	private Logger logg= LoggerFactory.getLogger(AdminstratorController.class);
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	public CategorieServiceImpl categorieService;
	

	

	
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			/*Authentication authentication,*/ HttpServletRequest request) {

	/*	if (authentication != null) {
			logger.info("Hola usuario autenticado, tu username es ".concat(authentication.getName()));

		}

		// Obtengo de una manera alternativa el objeto Authentication en este caso del
		// SecurityContextHolder que es común a todas las clases
		// del proyecto
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Replico lo de arriba con el securityContextHolder
		if (auth != null) {
			logger.info(
					"Utilizando forma stática con SecurityContextHolder.getContext().getAuthentication(), tu username es "
							.concat(auth.getName()));

		}

		// aquí implemento la validación del role del usuario logueado según el
		// procedimiento hasRole

		if (hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso"));
		}
		else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tiene acceso"));
		}

		SecurityContextHolderAwareRequestWrapper securityContext= new SecurityContextHolderAwareRequestWrapper(request,"ROLE_") ;
		
		if (securityContext.isUserInRole("ADMIN")){
			logger.info("forma usando SecurityContextHolderAwareRequestWrapper  Hola ".concat(auth.getName()).concat(" tienes acceso"));
		} else {
			logger.info(" forma usando SecurityContextHolderAwareRequestWrapper Hola ".concat(auth.getName()).concat(" NO tiene acceso"));
		};
		
		//aqui puede utilizar el request como tercer método para obtener el role, aquí si indicamos el nombre del role completo
		
		if (request.isUserInRole("ROLE_ADMIN")){
			logger.info("forma usando SecurityContextHolderAwareRequestWrapper  Hola ".concat(auth.getName()).concat(" tienes acceso"));
		} else {
			logger.info(" forma usando SecurityContextHolderAwareRequestWrapper Hola ".concat(auth.getName()).concat(" NO tiene acceso"));
		};
		*/
		
		
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Categorie> categories = categorieService.categoriePageFindAll(pageRequest);

		PageRender<Categorie> pageRender = new PageRender<Categorie>("/administrator", categories);
		model.addAttribute("titulo", "Categorie List");
		model.addAttribute("categories", categories);
		model.addAttribute("page", pageRender);
		return "administrator/home";
	}
	
	
	@GetMapping("/users")
	public String users(Model model) {
	    List<Usuario> users = userService.findAll();
	    System.out.println("Total users: " + users.size()); // Verificar el número de usuarios obtenidos
	    model.addAttribute("users", users);
	    return "administrator/users";
	}

	@GetMapping("/orders")
	public String getorders(Model model) {
		
		
		List<Order> orders= orderService.findAllOrder();
		 System.out.println("All Orders: " + orders.size());
		
		model.addAttribute("orders",orders);
		return "/administrator/orders";
	}

	@GetMapping("/detailorder/{id}")
	public String detalleOrder(Model model,@PathVariable  Integer id ) {
		
		logg.info("El id de la orden obtenida 	es {}:", id);
		
		Optional<Order> orderoptional= orderService.findById(id);
		Order order= orderoptional.get();
		
		 model.addAttribute("details", order.getDetail());
		return "/administrator/orderdetail";
		

		
	}





}