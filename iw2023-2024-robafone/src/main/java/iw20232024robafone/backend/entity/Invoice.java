package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Invoice extends AbstractEntity {
    @NotNull
    private LocalDateTime invoiceDate;

    @NotNull
    private String cost;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Servicio service;

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Servicio getService() { return service; }

    public void setService(Servicio service) { this.service = service; }
}
