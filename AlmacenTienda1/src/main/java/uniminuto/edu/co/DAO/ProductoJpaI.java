package uniminuto.edu.co.DAO;

import org.springframework.data.jpa.repository.JpaRepository;


import uniminuto.edu.co.model.Producto;


public interface ProductoJpaI extends JpaRepository<Producto, Integer> {
	Producto findByNombre(String nombre);
}
