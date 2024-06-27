package uniminuto.edu.co;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uniminuto.edu.co.DAO.ProductoJpaI;
import uniminuto.edu.co.controller.ProductoController;
import uniminuto.edu.co.model.Producto;

public class ProductoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductoJpaI productoRepository;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    public void testListarProductos() throws Exception {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto());
        productos.add(new Producto());

        when(productoRepository.findAll()).thenReturn(productos);

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista"))
                .andExpect(model().attributeExists("productos"))
                .andExpect(model().attribute("productos", productos));
    }

    @Test
    public void testMostrarFormularioNuevo() throws Exception {
        mockMvc.perform(get("/productos/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("formulario"))
                .andExpect(model().attributeExists("producto"));
    }

    @Test
    public void testGuardarProducto() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Producto Test");

        mockMvc.perform(post("/productos/nuevo")
                .flashAttr("producto", producto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/productos"));

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testMostrarFormularioEditar() throws Exception {
        Producto producto = new Producto();
        producto.setId(1);
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/productos/1/editar"))
                .andExpect(status().isOk())
                .andExpect(view().name("formulario"))
                .andExpect(model().attributeExists("producto"))
                .andExpect(model().attribute("producto", producto));
    }

    @Test
    public void testActualizarProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Actualizado");

        mockMvc.perform(post("/productos/1/editar")
                .flashAttr("producto", producto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/productos"));

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testEliminarProducto() throws Exception {
        mockMvc.perform(post("/productos/1/eliminar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/productos"));

        verify(productoRepository, times(1)).deleteById(1);
    }
}