package iw20232024robafone.backend.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.util.List;
@Transactional
@Entity
public class Servicio extends AbstractEntity {
    @NotEmpty
    private String type = "";

    @NotNull
    private String price;

    @NotNull
    private String description = "";

    @ManyToMany
    private List<Client> client;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Client> getClient() {
        return client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }
}
