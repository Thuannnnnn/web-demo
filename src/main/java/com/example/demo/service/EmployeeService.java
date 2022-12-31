package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
@Service
public class EmployeeService {
	    @Autowired
	    private EmployeeRepository repo;
	     
	    public List<Employee> listAll(String keyword) {
	        if (keyword != null) {
	            return repo.search(keyword);
	        }
	        return repo.findAll();
	    }
	     
	}

