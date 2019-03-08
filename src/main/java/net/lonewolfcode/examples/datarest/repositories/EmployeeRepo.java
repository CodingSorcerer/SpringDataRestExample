package net.lonewolfcode.examples.datarest.repositories;

import net.lonewolfcode.examples.datarest.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "employee")
public interface EmployeeRepo extends CrudRepository<Employee,String> {
}
