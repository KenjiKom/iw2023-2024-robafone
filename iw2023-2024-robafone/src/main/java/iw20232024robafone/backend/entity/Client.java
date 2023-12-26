package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Client extends Usuario {

    @NotNull
    private double monthlyVolume;

    @NotNull
    private double dataUsage;

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
}
