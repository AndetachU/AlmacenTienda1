package uniminuto.edu.co.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uniminuto.edu.co.DAO.UserDetailsServiceImpl;
@CrossOrigin(origins = "*")


@Controller
public class loginController {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	public loginController(UserDetailsServiceImpl userDetailsService) {
	    this.userDetailsService = userDetailsService;
	}
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
    @GetMapping("/loginPage")
    @PostMapping("/login")
    public String processLogin(@RequestParam(name = "username", required = false) String username,
    		 @RequestParam(name = "password", required = false) String password,
             RedirectAttributes redirectAttributes) {
    	if(username != null) {
			// Recuperar detalles del usuario desde la bas de datos
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    	
// Verificar la contraseña
      if (userDetails != null && passwordEncoder().matches(password, userDetails.getPassword())) {
    // Autenticar al usuario
	Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(authentication);

// Redirigir a una página de inicio o a la página deseada después del login exitoso
return "redirect:/formulario";
} else {
// Autenticación fallida: redirigir de vuelta a la página de login con un mensaje de error
redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
return "redirect:/loginPage";
}
    	}else {
    		return "loginPage";
    	}
    }
}