package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.hr.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

}
