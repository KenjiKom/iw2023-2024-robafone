package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
public class Sms extends AbstractEntity {
    @NotEmpty
    private Date smsDate;

    @NotEmpty
    private String message = "";

    @NotEmpty
    private String sender = "";

    @ManyToOne
    private Client client;

    public Date getSmsDate() { return smsDate; }

    public void setSmsDate(Date smsDate) { this.smsDate = smsDate; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }
}
