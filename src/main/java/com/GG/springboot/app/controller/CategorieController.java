package com.GG.springboot.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.GG.springboot.app.entity.Categorie;
import com.GG.springboot.app.entity.Product;
import com.GG.springboot.app.paginator.PageRender;
import com.GG.springboot.app.service.CategorieServiceImpl;
import com.GG.springboot.app.service.IUploadFileService;





@Controller
@RequestMapping("")
@SessionAttributes("categorie")
public class CategorieController {
	
	@Autowired
	public CategorieServiceImpl categorieService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	
	@RequestMapping(value = { "/categorie" }, method = RequestMethod.GET)
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

		PageRender<Categorie> pageRender = new PageRender<Categorie>("/categorie", categories);
		//Pasé a resolver el título en este caso desde el thymeleaf
	//	model.addAttribute("titulo", "Categorie List");
		model.addAttribute("categories", categories);
		model.addAttribute("page", pageRender);
		model.addAttribute("entidad", "category");
		return "categorie/categorielist";
	}
	
	
	
	@GetMapping(value = "/categorie/verproxcateg/{id}")
	public String verproxcateg(@RequestParam(name = "page", defaultValue = "0") int page,
			@PathVariable(value = "id") int id, Map<String, Object> model, RedirectAttributes flash) {

		Categorie categorie = categorieService.findById(id);
		if (categorie == null) {
			flash.addFlashAttribute("error", "The category dos not exist in the database");
			return "redirect:/";
		}

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Product> productos = categorieService.obtenerProductosPorCategoria(categorie, pageRequest);

		PageRender<Product> pageRender = new PageRender<Product>("/categorie", productos);

		model.put("categorie", categorie);
		model.put("titulo", "Category Detail :  " + categorie.getDesc_categorie());
		model.put("entidadActual", "categorias");
		model.put("busqueda", "productos");
		model.put("page", pageRender);

		return "categorie/ver";
	}
	
	@RequestMapping(value = "/categorie/form")
	public String crear(Map<String, Object> model) {

		Categorie categorie = new Categorie();
		model.put("categorie", categorie);
		model.put("titulo", "Product Category Form");
		model.put("entidadActual", "categorias");
		return "categorie/categories";
	}
	
	
	@RequestMapping(value = "/categorie/form", method = RequestMethod.POST)
	public String save(@Valid Categorie categorie, BindingResult result, Model model,

			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		
	

		model.addAttribute("titulo", "Category Form");
		 
		if (result.hasErrors()) {
			// defino un map para los errores
			//esto lo paso a manejar de forma automática en thymeleaf
		/*	Map<String,String> errores= new HashMap<>();
			// luego voy poblando el map con los errores
			result.getFieldErrors().forEach(err ->
			{errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));});
			model.addAttribute("error", errores);*/
			return "categorie/categories";
		}	

		
		if (!foto.isEmpty()) {

			
			if ( categorie.getId() > 0 && categorie.getFoto() != null
					&& categorie.getFoto().length() > 0) {

				uploadFileService.delete(categorie.getFoto());
			}

			String uniqueFilename = null;

			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			categorie.setFoto(uniqueFilename);
		}

		String mensajeFlash = (categorie.getId() != 0) ? "Categoria editada con éxito!"
				: "Categoría creada con éxito!";

		categorieService.save(categorie);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/administrator";

	}
	
	// método para eliminar una categoría por su id

	
		@RequestMapping(value = "/categorie/delete/{id}")
		public String eliminar(@PathVariable(value = "id") Integer id, RedirectAttributes flash) {

		    if (id > 0) {
		        Categorie categorie = categorieService.findById(id);

		        List<Product> products = categorieService.allProducts(id);

		        if (products == null || products.isEmpty()) {
		            categorieService.delete(id);
		            flash.addFlashAttribute("success", "Category successfully removed!");

		            if (categorie.getFoto() != null) {
		                if (uploadFileService.delete(categorie.getFoto())) {
		                    flash.addFlashAttribute("info", "Foto " + categorie.getFoto() + " successfully removed!");
		                } else {
		                    flash.addFlashAttribute("info", "Photo not found");
		                }
		            }
		        } else {
		            flash.addFlashAttribute("error", "Category cannot be deleted because it contains articles");
		        }
		    }

		    return "redirect:/categorie";
		}

		// metodo para editar una categoria en particular

		@RequestMapping(value = "/categorie/edit/{id}")
		public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

			Categorie categorie = null;

			if (id > 0) {
				categorie = categorieService.findById(id);
				if (categorie == null) {
					flash.addFlashAttribute("error", "The Category ID cannot be 0!");
					return "redirect:/administrator";
				}
			} else {
				flash.addFlashAttribute("error", "The Category ID cannot be 0!");
				return "redirect:/categorie";
			}

			model.put("categorie", categorie);
			model.put("titulo", "Edit Category");
			model.put("entidadActual", "categorias");
			return "categorie/categories";
		}

		// método para ver una categoría
		@GetMapping(value = "/ver/{id}")
		public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

			Categorie categorie = categorieService.findById(id);
			if (categorie == null) {
				flash.addFlashAttribute("error", "La categoría no existe en la base de datos");
				return "redirect:/categories";
			}

			model.put("categoria", categorie);
			model.put("titulo", "Category details :  " + categorie);
			model.put("entidadActual", "categorias");
			model.put("busqueda", "productos");
			
			return "categorie/viewcategory";
		}
		
		 @PostMapping("/searchcategory")
		    public String searchCategory(@RequestParam String nombre, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		        // Crear un objeto Pageable para la paginación
		        Pageable pageable = PageRequest.of(page, 4);

		        // Obtener todos los productos paginados
		        Page<Categorie> categoriePage = categorieService.categoriePageFindAll(pageable);
		        

		        // Filtrar los productos que contienen el nombre de búsqueda
		        List<Categorie> filteredCategories = categoriePage
		                .getContent()
		                .stream()
		                .filter(categorie -> categorie.getDesc_categorie() != null && categorie.getDesc_categorie().toLowerCase().contains(nombre.toLowerCase()))
		                .collect(Collectors.toList());

		        // Crear una nueva página de productos con los productos filtrados
		        Page<Categorie> filteredCategoriesPage = new PageImpl<>(filteredCategories, pageable, categoriePage.getTotalElements());
		        PageRender<Categorie> pageRender = new PageRender<Categorie>("/categorie",filteredCategoriesPage);
		        // Agregar los productos filtrados a la vista
		        model.addAttribute("categories", filteredCategories);
		        model.addAttribute("page", pageRender);
		    	model.addAttribute("entidad", "category");

		        // Resto del código para el renderizado de la vista...
		        return "categorie/categorielist";
		    }
		 

		    
		    

}
