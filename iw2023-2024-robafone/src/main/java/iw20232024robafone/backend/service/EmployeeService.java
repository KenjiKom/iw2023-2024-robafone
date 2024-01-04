package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmployeeService {
    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) { this.employeeRepository = employeeRepository; }

    public List<Employee> findAll() { return employeeRepository.findAll(); }

    public Optional<Employee> findEmployee(Employee employee) {return employeeRepository.findById(employee.getId()); }

    public long count() {
        return employeeRepository.count();
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void save(Employee employee) {
        if (employee == null) {
            LOGGER.log(Level.SEVERE, "Employee is null");
            return;
        }
        employeeRepository.save(employee);
    }
}
