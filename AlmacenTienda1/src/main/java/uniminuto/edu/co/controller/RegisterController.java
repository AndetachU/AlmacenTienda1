package uniminuto.edu.co.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uniminuto.edu.co.DAO.UserDetailsServiceImpl;
import uniminuto.edu.co.model.Usuario;

@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "registerPage";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  Model model) {
        if (userDetailsService.loadUserByUsername(username) != null) {
            model.addAttribute("errorMessage", "El nombre de usuario ya existe");
            return "registerPage";
        }

        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled((byte) 1); // 1 para habilitado, 0 para deshabilitado
        userDetailsService.saveUser(user);
        logger.info("Usuario registrado exitosamente: {}", username);
        return "redirect:/loginPage";
    }
}
