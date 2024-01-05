package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;
@Transactional
@Entity
public class Client extends Usuario {

    @NotNull
    private double monthlyVolume;

    @NotNull
    private double dataUsage;

    @ManyToMany
    private List<Servicio> services;

    public double getMonthlyVolume() {
        return monthlyVolume;
    }

    public void setMonthlyVolume(double monthlyVolume) {
        this.monthlyVolume = monthlyVolume;
    }

    public double getDataUsage() {
        return dataUsage;
    }

    public void setDataUsage(double dataUsage) {
        this.dataUsage = dataUsage;
    }

    public List<Servicio> getServices() { return services; }

    public void setServices(List<Servicio> services) { this.services = services; }
}
