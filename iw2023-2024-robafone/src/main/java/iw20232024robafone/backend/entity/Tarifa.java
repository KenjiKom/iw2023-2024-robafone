package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Tarifa extends AbstractEntity {
    @NotNull
    private String gigas;

    @NotNull
    private String precio;

    @NotNull
    private String type;


    public String getGigas() { return gigas; }

    public void setGigas(String gigas) { this.gigas = gigas; }

    public String getPrecio() { return precio; }

    public void setPrecio(String precio) { this.precio = precio; }

    public String getType() { return type; }

    public void setType(String type) { this.precio = type; }

    
}
