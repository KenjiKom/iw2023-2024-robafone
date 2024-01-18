package iw20232024robafone.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Complaint extends AbstractEntity {
    @NotNull
    private LocalDateTime dateComplaint;

    @NotNull
    private String resolverComment;

    @NotNull
    private Boolean resolved;

    @NotEmpty
    private String reason = "";

    @NotEmpty
    private String Message = "";

    @ManyToOne
    private Client client;

    public LocalDateTime getDateComplaint() { return dateComplaint; }

    public void setDateComplaint(LocalDateTime dateComplaint) { this.dateComplaint = dateComplaint; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }


    public String getResolverComment() { return resolverComment; }

    public void setResolverComment(String resolverComment) { this.resolverComment = resolverComment; }

    public Boolean getResolved() { return resolved; }

    public void setReasolved(Boolean bool) { this.resolved = bool; }

    public String getMessage() { return Message; }

    public void setMessage(String message) { Message = message; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }
    
}
