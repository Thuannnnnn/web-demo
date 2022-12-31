package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("SELECT p FROM Employee p WHERE p.productName LIKE %?1%")
	List<Employee> search(String productName);
	
	@Query("select a from Employee a")
    Page<Employee> findAllProducts(Pageable pageable);
}

