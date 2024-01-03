package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
public class Complaint extends AbstractEntity {
    @NotEmpty
    private Date dateComplaint;

    @NotEmpty
    private String reason = "";

    @NotEmpty
    private String Message = "";

    @ManyToOne
    private Client client;

    public Date getDateComplaint() { return dateComplaint; }

    public void setDateComplaint(Date dateComplaint) { this.dateComplaint = dateComplaint; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public String getMessage() { return Message; }

    public void setMessage(String message) { Message = message; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }
    
}
