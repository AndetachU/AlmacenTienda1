package uniminuto.edu.co.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniminuto.edu.co.DAO.ProductoJpaI;
import uniminuto.edu.co.model.Producto;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    ProductoJpaI productoRepository;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "lista";
    }
    @PostMapping("/productos/{id}/editar")
    public String editarProducto(@PathVariable Long id, @ModelAttribute Producto producto) {
        // Aquí deberías tener lógica para editar el producto con el ID proporcionado
        // Puedes usar el servicio para actualizar el producto en la base de datos
        productoRepository.save(producto);
        return "redirect:/formulario";  // Redirigir a la página deseada después de la edición
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    @PostMapping("/nuevo")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        model.addAttribute("producto", producto);
        return "formulario";
    }

    @PostMapping("/{id}/editar")
    public String actualizarProducto(@PathVariable int id, @ModelAttribute("producto") Producto producto) {
        producto.setId(id);
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarProducto(@PathVariable int id) {
        productoRepository.deleteById(id);
        return "redirect:/productos";
    }
}
