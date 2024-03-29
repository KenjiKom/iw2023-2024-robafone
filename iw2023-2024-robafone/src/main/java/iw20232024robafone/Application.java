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
                                      ServicioRepository servicioRepository, SmsRepository smsRepository, ComplaintRepository complaintRepository, TarifaRepository tarifaRepository) {

        return (args) -> {
            Client client1 = new Client();
            client1.setUsername("client1");
            client1.setFirstName("Pepe");
            client1.setLastName("Gonzalez");
            client1.setPhoneNumber("666666666");
            client1.setEmail("pepegonza@gmail.com");
            client1.setRoaming(false);
            client1.setDatosCompartidos(false);
            client1.setNumerosBloqueados(false);
            client1.setRole("CLIENT");
            client1.setPassword(SecurityService.passwordEncoder().encode("1234"));
            clientRepository.save(client1);

            Client client2 = new Client();
            client2.setUsername("client2");
            client2.setFirstName("Josep");
            client2.setLastName("Guardiola");
            client2.setPhoneNumber("123456789");
            client2.setEmail("pepguardiola@gmail.com");
            client2.setRoaming(false);
            client2.setDatosCompartidos(false);
            client2.setNumerosBloqueados(false);
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

            Llamada llamada3 = new Llamada();
            llamada3.setCallDate(date1);
            llamada3.setDuration(3.4);
            llamada3.setSender("Antonio");
            llamada3.setClient(client1);
            llamadaRepository.save(llamada3);

            Llamada llamada4 = new Llamada();
            llamada4.setCallDate(date1);
            llamada4.setDuration(1.6);
            llamada4.setSender("Maria");
            llamada4.setClient(client1);
            llamadaRepository.save(llamada4);

            Llamada llamada5 = new Llamada();
            llamada5.setCallDate(date1);
            llamada5.setDuration(6.4);
            llamada5.setSender("Manoli");
            llamada5.setClient(client1);
            llamadaRepository.save(llamada5);

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
            sms2.setMessage("Vamos al gym?");
            sms2.setSender("Miguel");
            sms2.setClient(client1);
            smsRepository.save(sms2);

            Sms sms3 = new Sms();
            sms3.setSmsDate(date4);
            sms3.setMessage("Cata de vinos");
            sms3.setSender("Luis");
            sms3.setClient(client1);
            smsRepository.save(sms3);

            Sms sms4 = new Sms();
            sms4.setSmsDate(date4);
            sms4.setMessage("Me voy de la carrera");
            sms4.setSender("Alejandro");
            sms4.setClient(client1);
            smsRepository.save(sms4);

            Sms sms5 = new Sms();
            sms5.setSmsDate(date4);
            sms5.setMessage("Me regalaron EDNL");
            sms5.setSender("Antonio");
            sms5.setClient(client1);
            smsRepository.save(sms5);

            Servicio service1 = new Servicio();
            Client listClients1 = new Client();
            service1.setType("Fibra");
            service1.setPrice("19.99");
            service1.setGigas("10");
            service1.setDateService(LocalDateTime.now());
            service1.setValidated(true);
            service1.setDescription("Especificaciones de tu router, además de detalles de tu oferta contratada como la velocidad de subida y bajada");
            List<Client> cliente = new ArrayList<Client>();
            cliente.add(client2);
            service1.setClient(client1);
            servicioRepository.save(service1);


            Servicio service2 = new Servicio();
            List<Client> listClients2 = new ArrayList<Client>();
            listClients2.add(client1);
            listClients2.add(client2);
            service2.setType("Fijo");
            service2.setPrice("15.99");
            service2.setGigas("5");
            service2.setDateService(LocalDateTime.now());
            service2.setValidated(false);
            service2.setDescription("Conocer qué tiene y cómo es la tarifa que esté actualmente activada, además de todos los extras y la actualización del consumo realizado.");
            List<Client> clienteA = new ArrayList<Client>();
            clienteA.add(client1);
            service2.setClient(client1);
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
            complaint1.setReasolved(false);
            complaint1.setResolverComment("No Resolver Comment");
            complaintRepository.save(complaint1);

            Complaint complaint2 = new Complaint();
            complaint2.setClient(client2);
            complaint2.setReason("Bad Customer Service");
            complaint2.setMessage("Migue can bench 225");
            complaint2.setDateComplaint(LocalDateTime.now());
            complaint2.setReasolved(false);
            complaint2.setResolverComment("No Resolver Comment");
            complaintRepository.save(complaint2);

            Tarifa tarifaFifra1 = new Tarifa();
            tarifaFifra1.setGigas("5");
            tarifaFifra1.setPrecio("15.99");
            tarifaFifra1.setTipo("Fiber");
            tarifaRepository.save(tarifaFifra1);

            Tarifa tarifaFifra2 = new Tarifa();
            tarifaFifra2.setGigas("10");
            tarifaFifra2.setPrecio("19.99");
            tarifaFifra2.setTipo("Fiber");
            tarifaRepository.save(tarifaFifra2);

            Tarifa tarifaFifra3 = new Tarifa();
            tarifaFifra3.setGigas("15");
            tarifaFifra3.setPrecio("23.99");
            tarifaFifra3.setTipo("Fiber");
            tarifaRepository.save(tarifaFifra3);

            Tarifa tarifaPhone1 = new Tarifa();
            tarifaPhone1.setGigas("5");
            tarifaPhone1.setPrecio("10.99");
            tarifaPhone1.setTipo("Phone");
            tarifaRepository.save(tarifaPhone1);

            Tarifa tarifaPhone2 = new Tarifa();
            tarifaPhone2.setGigas("10");
            tarifaPhone2.setPrecio("15.99");
            tarifaPhone2.setTipo("Phone");
            tarifaRepository.save(tarifaPhone2);

            Tarifa tarifaPhone3 = new Tarifa();
            tarifaPhone3.setGigas("15");
            tarifaPhone3.setPrecio("20.99");
            tarifaPhone3.setTipo("Phone");
            tarifaRepository.save(tarifaPhone3);


        };
    }
}

