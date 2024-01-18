package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @OneToMany
    private List<Servicio> services;

    @NotNull
    private Boolean roaming;
    @NotNull
    private Boolean datosCompartidos;
    @NotNull
    private Boolean numerosBloqueados;
    public boolean getRoaming() {return roaming;}
    public void setRoaming(boolean roaming) {
        this.roaming = roaming;
    }
    public boolean getDatosCompartidos() {return datosCompartidos;}
    public void setDatosCompartidos(boolean datosCompartidos) {
        this.datosCompartidos = datosCompartidos;
    }
    public boolean getNumerosBloqueados() {return numerosBloqueados;}
    public void setNumerosBloqueados(boolean numerosBloqueados) {
        this.numerosBloqueados = numerosBloqueados;
    }

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
