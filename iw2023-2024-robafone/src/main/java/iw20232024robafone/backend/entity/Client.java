package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Client extends Usuario {

    @NotNull
    private double monthlyVolume;

    @NotNull
    private double dataUsage;

    @ManyToMany
    private List<Service> services;

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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
