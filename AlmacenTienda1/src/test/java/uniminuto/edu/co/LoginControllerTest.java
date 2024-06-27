package uniminuto.edu.co;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;

import uniminuto.edu.co.DAO.UserDetailsServiceImpl;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        // Datos del usuario válido
        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername("validUser")
            .password("encodedPassword") // Contraseña codificada almacenada
            .roles("USER")
            .build();

        // Configuración de simulación de UserDetailsService
        given(userDetailsService.loadUserByUsername("validUser")).willReturn(userDetails);

        // Configuración de simulación de PasswordEncoder
        given(passwordEncoder.matches("validPassword", "encodedPassword")).willReturn(true); // Contraseña válida
        given(passwordEncoder.matches("invalidPassword", "encodedPassword")).willReturn(false); // Contraseña inválida
    }

    @Test
    public void testLoginWithValidCredentials() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "validUser")
                .param("password", "validPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/formulario"));
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "validUser")
                .param("password", "invalidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/loginPage"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    public void testLoginWithNullUsername() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/loginPage"));
    }
}
