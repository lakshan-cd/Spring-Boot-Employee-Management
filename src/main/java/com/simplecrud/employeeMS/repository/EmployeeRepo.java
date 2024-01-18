package com.simplecrud.employeeMS.repository;

import com.simplecrud.employeeMS.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee , Integer> {
//    Page<Employee> findAll(Pageable pageable);
}
