package com.GG.springboot.app.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.GG.springboot.app.entity.Categorie;
import com.GG.springboot.app.entity.Order;
import com.GG.springboot.app.entity.OrderDetail;
import com.GG.springboot.app.entity.Product;
import com.GG.springboot.app.entity.Usuario;
import com.GG.springboot.app.paginator.PageRender;
import com.GG.springboot.app.service.CategorieServiceImpl;
import com.GG.springboot.app.service.IOrderDetailService;
import com.GG.springboot.app.service.IOrderService;
import com.GG.springboot.app.service.IProductServiceImpl;
import com.GG.springboot.app.service.IUploadFileService;
import com.GG.springboot.app.service.IUserService;


@Controller
public class HomeController {
	@Autowired
	private IUploadFileService uploadFileService;

	@Autowired
	public CategorieServiceImpl categorieService;

	@Autowired
	public IProductServiceImpl productService;

	@Autowired
	public IOrderService orderService;

	@Autowired
	public IUserService userService;

	@Autowired
	public IOrderDetailService iOrderDetailService;

	private final Logger log = LoggerFactory.getLogger(HomeController.class);

	List<OrderDetail> detail = new ArrayList<OrderDetail>();
	Order order = new Order();

	// @Secured("ROLE_USER")
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	/*
	 * @Secured("ROLE_USER")
	 * 
	 * @GetMapping(value = "/ver/{id}") public String ver(@PathVariable(value =
	 * "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
	 * 
	 * Cliente cliente = clienteService.findOne(id); if (cliente == null) {
	 * flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
	 * return "redirect:/listar"; }
	 * 
	 * model.put("cliente", cliente); model.put("titulo", "Detalle cliente: " +
	 * cliente.getNombre()); return "ver"; }
	 */

	@RequestMapping(value = { "/categorielist", "/" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,Authentication authentication,
			   HttpServletRequest request,HttpSession session) {
		
	
		
		model.addAttribute("session", session.getAttribute("idusuario"));
		log.info("La Sesion del usuario : {} ", session.getAttribute("idusuario"));
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Categorie> categories = categorieService.categoriePageFindAll(pageRequest);

		PageRender<Categorie> pageRender = new PageRender<Categorie>("/categorielist", categories);
		model.addAttribute("titulo", "Categorie List");
		model.addAttribute("categories", categories);
		model.addAttribute("page", pageRender);
		return "home";
	}

	@PostMapping("/cart")
	public String addCar(@RequestParam Integer id, @RequestParam float quantity, Model model, HttpSession session) {
		OrderDetail orderDetail = new OrderDetail();
		Product product = new Product();
		double TotalAmount = 0;

		Product Product = productService.findById(id);

		log.info(" Added product {}", Product);
		log.info("Quantity{}", quantity);

		orderDetail.setQuantity(quantity);
		orderDetail.setPrice(Product.getPrecio());
		orderDetail.setName(Product.getDesc_product());
		orderDetail.setTotal(Product.getPrecio() * quantity);
		orderDetail.setProduct(Product);

		Integer idProducto = Product.getId();
		boolean ingresado = detail.stream().anyMatch(p -> p.getProduct().getId() == idProducto);

		if (!ingresado) {
			detail.add(orderDetail);
		}

		TotalAmount = detail.stream().mapToDouble(dt -> dt.calculateAmount()).sum();

		log.info("Total Sum of Order Amount {}", TotalAmount);
		order.setTotal(TotalAmount);
		
		log.info("Total Sum of Order Amount 2 {}", order.getTotal());
		model.addAttribute("cart", detail);
		model.addAttribute("order", order);
		session.setAttribute("order", order);
	    session.setAttribute("detail", detail);

		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "user/cart";
	}

	@GetMapping("/getCart")
	public String getCart(Model model) {
		model.addAttribute("cart", detail);
		model.addAttribute("order", order);

		return "user/cart";
	}

	@GetMapping("/delete/cart/{id}")
	public String deleteProductCart(@PathVariable Integer id, Model model) {
		List<OrderDetail> ordenesNueva = new ArrayList<OrderDetail>();

		for (OrderDetail detalleOrden : detail) {
			if (detalleOrden.getProduct().getId() != id) {
				ordenesNueva.add(detalleOrden);
			}
		}

		detail = ordenesNueva;
		double TotalAmount = 0;
		TotalAmount = detail.stream().mapToDouble(dt -> dt.getTotal()).sum();
		order.setTotal(TotalAmount);

		model.addAttribute("cart", detail);
		model.addAttribute("order", order);

		return "user/cart";
	}

	@GetMapping("/order")
	public String order(Model model, HttpSession session,Authentication authentication) {
 
		// Aquí tomo la session del usuario que es un objeto, lo paso a string y luego
		// lo parseo a integer para fecién ahí si pasarlo como parámetro a findbyID
		
		

		if (authentication!=null) {
			Optional<Usuario> userOptional= userService.findByUsername(authentication.getName());
			Usuario user=userOptional.get();
			log.info("The User : " + authentication.getName() + "  you have logged in successfully");
			
			log.info("The email : " + user.toString() );
			  // Imprimir el objeto order
	        log.info("Order antes de añadirlo al modelo: {}", order);
	        
	    	
	        	
	        Order order = (Order) session.getAttribute("order");
	        List<OrderDetail> detail = (List<OrderDetail>) session.getAttribute("detail");
	        
			model.addAttribute("cart", detail);
			model.addAttribute("order", order);
			model.addAttribute("user", user);
			return "user/ordersummary";
		} else {
			return "user/ordersummary";
		}

	}

	@GetMapping("/saveOrder")

	public String saveOrder(Authentication authentication,RedirectAttributes flash) {
		Date datecreate = new Date();
		// Obtengo el usuario
		Optional<Usuario> userOptional= userService.findByUsername(authentication.getName());
		Usuario user=userOptional.get();

		order.setDatecreate(datecreate);
		order.setNumber(orderService.generarNumeroOrden());
		order.setUsuario(user);
		
		orderService.saveOrder(order);

		// Guardamos detalle de la orden
		// Recorro todo el detalle y voy guardando la orden

		for (OrderDetail dt : detail) {

			dt.setOrden(order);

			iOrderDetailService.saveOrderDetail(dt);

		}
		
		String mensajeFlash = (user.getUsername()!="") ? "Order save successfully !"
				: "Order save successfully !";
		flash.addFlashAttribute("success", mensajeFlash);
		// Limpiar orden y lista

		order = new Order();

		detail.clear();

		return "redirect:/";
	}

	
	@GetMapping("/shopping")
	public String getcompras(Model model,Authentication authentication) {



		Optional<Usuario> userOptional= userService.findByUsername(authentication.getName());
		Usuario user=userOptional.get();

		List<Order> orders = orderService.findByUser(user);

		model.addAttribute("orders", orders);
		return "/user/shopping";
	}
	
	// Permite obtener el detalle de la orde por ID
	@GetMapping("/detail/{id}")
	public String detalleCompra(@PathVariable Integer id, Authentication authentication, Model model) {

		Optional<Order> orden =orderService.findById(id);

		model.addAttribute("details", orden.get().getDetail());

	
		return "user/shoppingdetail";
	}

	
}
