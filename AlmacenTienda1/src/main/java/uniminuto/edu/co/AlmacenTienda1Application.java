package uniminuto.edu.co;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EntityScan(basePackages = {"uniminuto.edu.co.model"})
@EnableJpaRepositories(basePackages = {"uniminuto.edu.co.DAO"})
@ComponentScan(basePackages = {"uniminuto.edu.co.security",
		"uniminuto.edu.co.controller",
		"uniminuto.edu.co.DAO",
		"uniminuto.edu.co"})
public class AlmacenTienda1Application {

	public static void main(String[] args) {
		SpringApplication.run(AlmacenTienda1Application.class, args);
	}

}
