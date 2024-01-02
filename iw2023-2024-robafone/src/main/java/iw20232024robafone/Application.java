package iw20232024robafone;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import iw20232024robafone.backend.entity.Usuario;
import iw20232024robafone.backend.repository.UsuarioRepository;
import iw20232024robafone.security.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Theme(value="iw-robafone", variant= Lumo.DARK)
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UsuarioRepository usuarioRepository) {

        return (args) -> {
            Usuario u = new Usuario();
            u.setUsername("user");
            u.setFirstName("Pepe");
            u.setLastName("Gonzalez");
            u.setPhoneNumber("66666666");
            u.setEmail("pepegonza@gmail.com");
            u.setRole("CLIENT");
            u.setPassword(SecurityService.passwordEncoder().encode("1234"));
            usuarioRepository.save(u);
        };
    }
}

