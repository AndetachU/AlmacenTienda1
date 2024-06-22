package uniminuto.edu.co.controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import uniminuto.edu.co.DAO.ProductoJpaI;
import uniminuto.edu.co.model.Producto;

@Controller
public class MainController {
    @Autowired
    ProductoJpaI productoRepository;


    @GetMapping("/")
    public String index() {
        return "redirect:/loginPage"; // Redirige al usuario a la página de inicio de sesión
    }
	@GetMapping("/formulario")
    public String formulario(Model model) {
		 Producto producto = new Producto(); // Crear un nuevo producto o recuperarlo de la base de datos según sea necesario
		    model.addAttribute("producto", producto);
		    return "formulario";
		}
	
    @GetMapping("/lista")
    public String lista(Model model) {
    	Producto producto = new Producto(); 
    	model.addAttribute("productos", productoRepository.findAll());
        return "lista"; // Devuelve el nombre de la plantilla Thymeleaf para la página lista
    }
    

}
