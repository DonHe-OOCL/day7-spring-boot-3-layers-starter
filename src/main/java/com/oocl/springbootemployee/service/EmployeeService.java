package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeCreateInvalidException;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.IEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final IEmployeeRepository employeeRepository;
    public EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    public Employee creat(Employee employee) {
        if (checkCreateEmployeeInvalid(employee)) {
            throw new EmployeeCreateInvalidException();
        }
        return employeeRepository.addEmployee(employee);
    }

    private boolean checkCreateEmployeeInvalid(Employee employee) {
        return employee.getAge() < 18
                || employee.getAge() > 65
                || (employee.getAge() > 30 && employee.getSalary() < 20000.0);
    }

    public Employee update(Integer employeeId, Employee employee){
        Employee employeeExisted = employeeRepository.getEmployeeById(employeeId);

        var nameToUpdate = employee.getName() == null ? employeeExisted.getName() : employee.getName();
        var ageToUpdate = employee.getAge() == null ? employeeExisted.getAge() : employee.getAge();
        var genderToUpdate = employee.getGender() == null ? employeeExisted.getGender() : employee.getGender();
        var salaryToUpdate = employee.getSalary() == null ? employeeExisted.getSalary() : employee.getSalary();

        final var employeeToUpdate = new Employee(employeeId, nameToUpdate, ageToUpdate, genderToUpdate, salaryToUpdate);

        return employeeRepository.updateEmployee(employeeId, employeeToUpdate);
    }
}
