package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Call extends AbstractEntity {
    @NotNull
    private LocalDateTime callDate;

    @NotNull
    private Double duration;

    @NotEmpty
    private String sender = "";

    @ManyToOne
    private Client client;

    public LocalDateTime getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDateTime callDate) {
        this.callDate = callDate;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
