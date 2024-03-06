package com.GG.springboot.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
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
import com.GG.springboot.app.service.ICategorieService;
import com.GG.springboot.app.service.IProductService;
import com.GG.springboot.app.service.IUploadFileService;




@Controller
@RequestMapping("")
@SessionAttributes("product")
public class ProductController {

	@Autowired
	private IProductService iProductService;
	
	@Autowired
	
	private ICategorieService categorieService;
	
	
	@Autowired
	private IUploadFileService uploadFileService;

	SessionStatus HttpSession;
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/products")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);
		
		Page<Product> products = iProductService.AllItems(pageRequest);


		PageRender<Product> pageRender = new PageRender<Product>("/products", products);
		//quito el Titulo porque lo estoy resolviendo desde el thymeleaf
		//model.addAttribute("titulo", "Products List");
		model.addAttribute("products", products);
		model.addAttribute("page", pageRender);
		model.addAttribute("entidad", "product");

		return "product/listproducts";
	}
	
	
	
	// cuando le hacemos click en crear nuevo artículo hace un get que carga 
	// el formulario vació con un nuevo objeto artículo para llenar y dar el alta con el id en null
	// que permitirá crear dicho registro con un id incremental
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/products/form")
	public String crear(Map<String, Object> model) {

		Product product = new Product();
		model.put("product", product);
		model.put("titulo", "Product Form");
		model.put("entidadActual", "productos");
	    List<Categorie> categories = categorieService.categorieFindAll(); // Debes implementar este método
	    model.put("categories", categories);
		return "product/products";
	}
	
	// método para guardar el producto
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/products/form", method = RequestMethod.POST)
	public String guardar(@Valid Product product, BindingResult result, Model model,

			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Producto");
			return "product/products";
		}

		if (!foto.isEmpty()) {

			if ( product.getId() > 0 && product.getFoto() != null
					&& product.getFoto().length() > 0) {

				uploadFileService.delete(product.getFoto());
			}

			String uniqueFilename = null;

			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "You have uploaded correctly '" + uniqueFilename + "'");

			product.setFoto(uniqueFilename);
		}

		String mensajeFlash = (product.getId() != 0) ? "Successfully edited product!"
				: "Successfully created product!";

		iProductService.save(product);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/products";

	}

	// metodo para editar un producto en particular*/

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/products/edit/{id}")
	public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Product product = null;

		if (id > 0) {
			product = iProductService.findById(id);
			if (product== null) {
				flash.addFlashAttribute("error", "The Product ID does not exist !");
				return "redirect:/products";
			}
		} else {
			flash.addFlashAttribute("error", "The Product ID does not exist!");
			return "redirect:/productos";
		}
		List<Categorie> categories = categorieService.categorieFindAll();

		model.put("product", product);
		model.put("titulo", "Editar Producto");
		model.put("entidadActual", "productos");
		model.put("categories", categories);
		return "product/products";
	}
	
		@Secured("ROLE_ADMIN")
	// método para eliminar una categoría por su id
		@RequestMapping(value = "/products/delete/{id}")
		public String eliminar(@PathVariable(value = "id") Integer id, RedirectAttributes flash) {

			if (id > 0) {
				Product product = iProductService.findById(id);

				iProductService.delete(id);
				flash.addFlashAttribute("success", "Product successfully removed!");
				if (product.getFoto() != null) {

					if (uploadFileService.delete(product.getFoto())) {
						flash.addFlashAttribute("info", "Photo " + product.getFoto() + " successfully removed!");
					} else {

						flash.addFlashAttribute("info", "Photo not found");

					}
				}
			}

			return "redirect:/products";
		}

	
	//método para ver un producto
	@GetMapping(value = "/products/view/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Product product = iProductService.findById(id);
		if (product == null) {
			flash.addFlashAttribute("error", "Product doesnt exists");
			return "redirect:/product";
		}

		model.put("product", product);
		model.put("titulo", "Product Detail  :  " + product);
		model.put("entidadActual", "productos");
		return "product/view";
	}
	
  
    
    @PostMapping("/searchproduct")
    public String searchProduct(@RequestParam String nombre, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        // Crear un objeto Pageable para la paginación
        Pageable pageable = PageRequest.of(page, 4);

        // Obtener todos los productos paginados
        Page<Product> productsPage = iProductService.AllItems(pageable);
        

        // Filtrar los productos que contienen el nombre de búsqueda
        List<Product> filteredProducts = productsPage
                .getContent()
                .stream()
                .filter(product -> product.getDesc_product() != null && product.getDesc_product().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());

        // Crear una nueva página de productos con los productos filtrados
        Page<Product> filteredProductsPage = new PageImpl<>(filteredProducts, pageable, productsPage.getTotalElements());
        PageRender<Product> pageRender = new PageRender<Product>("/products",filteredProductsPage);
        // Agregar los productos filtrados a la vista
        model.addAttribute("products", filteredProductsPage);
        model.addAttribute("page", pageRender);
    	model.addAttribute("entidad", "product");

        // Resto del código para el renderizado de la vista...
        return "product/listproducts";
    }
    


}
