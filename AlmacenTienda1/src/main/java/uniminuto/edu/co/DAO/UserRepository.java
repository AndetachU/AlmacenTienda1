package uniminuto.edu.co.DAO;
import org.springframework.data.jpa.repository.JpaRepository;

import uniminuto.edu.co.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByUsername(String username);
}


