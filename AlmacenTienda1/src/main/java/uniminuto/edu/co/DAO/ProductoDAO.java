package uniminuto.edu.co.DAO;

import java.util.List;

import uniminuto.edu.co.model.Producto;

public interface ProductoDAO {
    Producto searchDatoId(int id);
    List<Producto> getAllProductos();
    Producto addProducto(Producto producto);
    Producto update(Producto producto);
    void delete(int id);
}