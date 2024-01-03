package iw20232024robafone.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee extends Usuario {

    @NotNull
    private int department;

    @OneToMany(mappedBy = "Employee")
    private Set<Complaint> complaints = new HashSet<Complaint>();

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }
}
