package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping("/api/v1")

public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	private EmployeeService employeeService;
	//Search
	@GetMapping("/search")
    public List<Employee> FindAll(@RequestParam Optional <String> productName) {
//        List<Employee> listEmployee = employeeRepository.search(keyword);
//        model.addAttribute("listEmployee", listEmployee);
//        model.addAttribute("keyword", keyword);
         
        return employeeRepository.search(productName.orElse(null));
    }
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
			return employeeRepository.findAll();
	}
		//create employee rest api
	@PostMapping("/employees")
	public Employee creatEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	@GetMapping("/pagination")
	public Page<Employee> productsPageable( Pageable pageable) {
	    return employeeRepository.findAll(pageable);
	}
	
	
	
	
	
	
	//get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exits with id :"+id));
		return ResponseEntity.ok(employee);
	}

//update employees rest api
@PutMapping("/employees/{id}")
public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
	Employee employee = employeeRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Employee not exits with id :"+id));
	employee.setProductName(employeeDetails.getProductName());
	employee.setPrice(employeeDetails.getPrice());
	employee.setPriceOld(employeeDetails.getPriceOld());
	employee.setDescription(employeeDetails.getDescription());
	employee.setPriceOld(employeeDetails.getPriceOld());
	employee.setStatus(employeeDetails.getStatus());
	employee.setInsurance(employeeDetails.getInsurance());
	employee.setBrandId(employeeDetails.getBrandId());
	employee.setProductId(employeeDetails.getProductId());
	employee.setImage(employeeDetails.getImage());
	
	
	
	
	
	Employee updatedEmployee =employeeRepository.save(employee);
	return ResponseEntity.ok(updatedEmployee);
} 
//deleta employees rest api 
@DeleteMapping("/employees/{id}")
public ResponseEntity <Map<String,Boolean>>deleteEmployee(@PathVariable Long id){
	Employee employee = employeeRepository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("Employee not exits with id :"+id));
	employeeRepository.delete(employee);
	Map<String,Boolean> reponse =new HashMap<>();
	reponse.put("deleted",Boolean.TRUE);
	return ResponseEntity.ok(reponse);
}



}

