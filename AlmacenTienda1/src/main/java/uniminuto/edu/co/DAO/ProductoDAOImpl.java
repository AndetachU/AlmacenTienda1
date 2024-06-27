package uniminuto.edu.co.DAO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import uniminuto.edu.co.model.Producto;

@Repository
public class ProductoDAOImpl implements ProductoDAO {


 @Autowired
	ProductoJpaI productoJpa;
	
 @Override
 public Producto searchDatoId(int id) {
	 return productoJpa.findById(id).orElse(null);
 }
 public Producto findByNombre(String nombre) {
	 return productoJpa.findByNombre(nombre);
}
 @Override
 public List<Producto> getAllProductos() { 
	return productoJpa.findAll();
 }

 @Override
 public Producto addProducto(Producto producto) {
     
     return productoJpa.save(producto);
 }

 @Override
 public Producto update(Producto producto) {
	 return productoJpa.save(producto);
 }

 @Override
 public void delete(int id) {
     productoJpa.deleteById(id);
 }
}
