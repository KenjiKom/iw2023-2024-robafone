package iw20232024robafone;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import iw20232024robafone.backend.entity.*;
import iw20232024robafone.backend.repository.*;
import iw20232024robafone.security.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public CommandLineRunner loadData(ClientRepository clientRepository, EmployeeRepository employeeRepository, LlamadaRepository llamadaRepository, InvoiceRepository invoiceRepository,
                                      ServicioRepository servicioRepository, SmsRepository smsRepository, ComplaintRepository complaintRepository) {

        return (args) -> {
            Client client1 = new Client();
            client1.setUsername("client1");
            client1.setFirstName("Pepe");
            client1.setLastName("Gonzalez");
            client1.setPhoneNumber("666666666");
            client1.setEmail("pepegonza@gmail.com");
            client1.setRole("CLIENT");
            client1.setPassword(SecurityService.passwordEncoder().encode("1234"));
            clientRepository.save(client1);

            Client client2 = new Client();
            client2.setUsername("client2");
            client2.setFirstName("Josep");
            client2.setLastName("Guardiola");
            client2.setPhoneNumber("123456789");
            client2.setEmail("pepguardiola@gmail.com");
            client2.setRole("CLIENT");
            client2.setPassword(SecurityService.passwordEncoder().encode("barcelona"));
            clientRepository.save(client2);

            Employee employee1 = new Employee();
            employee1.setUsername("employee1");
            employee1.setFirstName("Maria");
            employee1.setLastName("Fernandez");
            employee1.setPhoneNumber("654332322");
            employee1.setEmail("mariafer@gmail.com");
            employee1.setRole("EMPLOYEE");
            employee1.setPassword(SecurityService.passwordEncoder().encode("5678"));
            employeeRepository.save(employee1);

            Employee employee2 = new Employee();
            employee2.setUsername("employee2");
            employee2.setFirstName("Antonio");
            employee2.setLastName("Roldan");
            employee2.setPhoneNumber("444555666");
            employee2.setEmail("awenoweno@gmail.com");
            employee2.setRole("EMPLOYEE");
            employee2.setPassword(SecurityService.passwordEncoder().encode("yonosabia"));
            employeeRepository.save(employee2);

            Llamada llamada1 = new Llamada();
            LocalDateTime date1 = LocalDateTime.now();
            llamada1.setCallDate(date1);
            llamada1.setDuration(1.4);
            llamada1.setSender("Manolo");
            llamada1.setClient(client1);
            llamadaRepository.save(llamada1);

            Llamada llamada2 = new Llamada();
            LocalDateTime date2 = LocalDateTime.now();
            llamada2.setCallDate(date2);
            llamada2.setDuration(2.2);
            llamada2.setSender("Messi");
            llamada2.setClient(client2);
            llamadaRepository.save(llamada2);

            Sms sms1 = new Sms();
            LocalDateTime date3 = LocalDateTime.now();
            sms1.setSmsDate(date3);
            sms1.setMessage("Cómo están muchacho");
            sms1.setSender("Benson");
            sms1.setClient(client2);
            smsRepository.save(sms1);

            Sms sms2 = new Sms();
            LocalDateTime date4 = LocalDateTime.now();
            sms2.setSmsDate(date4);
            sms2.setMessage("Mi primera chamba");
            sms2.setSender("Eladio");
            sms2.setClient(client1);
            smsRepository.save(sms2);

            Servicio service1 = new Servicio();
            List<Client> listClients1 = new ArrayList<Client>();
            listClients1.add(client1);
            service1.setType("Fibra");
            service1.setPrice("19.99");
            service1.setDescription("Especificaciones de tu router, además de detalles de tu oferta contratada como la velocidad de subida y bajada");
            List<Client> cliente = new ArrayList<Client>();
            cliente.add(client2);
            service1.setClient(cliente);
            servicioRepository.save(service1);

            Servicio service2 = new Servicio();
            List<Client> listClients2 = new ArrayList<Client>();
            listClients2.add(client1);
            listClients2.add(client2);
            service2.setType("Fijo");
            service2.setPrice("15.99");
            service2.setDescription("Conocer qué tiene y cómo es la tarifa que esté actualmente activada, además de todos los extras y la actualización del consumo realizado.");
            List<Client> clienteA = new ArrayList<Client>();
            clienteA.add(client1);
            service2.setClient(clienteA);
            servicioRepository.save(service2);

            Invoice invoice1 = new Invoice();
            LocalDateTime date5 = LocalDateTime.now();
            invoice1.setInvoiceDate(date5);
            invoice1.setCost("5.5");
            invoice1.setEmployee(employee1);
            invoice1.setClient(client1);
            invoice1.setService(service1);
            invoiceRepository.save(invoice1);

            Invoice invoice2 = new Invoice();
            LocalDateTime date6 = LocalDateTime.now();
            invoice2.setInvoiceDate(date6);
            invoice2.setCost("7.92");
            invoice2.setEmployee(employee2);
            invoice2.setClient(client2);
            invoice2.setService(service2);
            invoiceRepository.save(invoice2);

            Complaint complaint1 = new Complaint();
            complaint1.setClient(client1);
            complaint1.setReason("Bad Service");
            complaint1.setMessage("This Roldan agent was so rude");
            complaint1.setDateComplaint(LocalDateTime.now());
            complaintRepository.save(complaint1);

            Complaint complaint2 = new Complaint();
            complaint2.setClient(client2);
            complaint2.setReason("Bad Customer Service");
            complaint2.setMessage("Migue can bench 225");
            complaint2.setDateComplaint(LocalDateTime.now());
            complaintRepository.save(complaint2);
        };
    }
}

