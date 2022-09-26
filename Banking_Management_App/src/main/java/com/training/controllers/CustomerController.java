package com.training.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.training.entity.Customer;
import com.training.exceptions.ResourceNotFoundException;
import com.training.helper.CSVHelper;
import com.training.message.ResponseMessage;
import com.training.repository.CustomerRepository;
import com.training.service.CSVService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private CSVService fileService;
	
	//get all the Customers -- GetMapping
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	//get the customer by Id --getMapping
	@GetMapping("/customers/{cust_id}")
	public ResponseEntity<Customer> getEmployeeById(@PathVariable Long cust_id) {
		Customer customer = customerRepository.findById(cust_id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with id :" + cust_id));
		return ResponseEntity.ok(customer);
	}
	
	//create a new customer -- post mappping
	@PostMapping("/customers")
	public Customer createEmployee(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}
	
	//update a customer -- put mapping
	@PutMapping("/customers/{cust_id}")
	public ResponseEntity<Customer> updateEmployee(@PathVariable Long cust_id, @RequestBody Customer customerDetails){
		Customer customer = customerRepository.findById(cust_id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + cust_id));
		
		customer.setName(customerDetails.getName());
		customer.setEmail(customerDetails.getEmail());
		customer.setAddress(customerDetails.getAddress());
		customer.setBalance(customerDetails.getBalance());
		customer.setPhone(customerDetails.getPhone());

		
		Customer updatedCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(updatedCustomer);
	}
	
	//delete a customer -- delete mapping
	@DeleteMapping("/customers/{cust_id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long cust_id){
		Customer customer = customerRepository.findById(cust_id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + cust_id));
		
		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	//CSV Batch
	
	
	  
	  
	  @PostMapping("/upload") public ResponseEntity<ResponseMessage>
	  uploadFile(@RequestParam("file") MultipartFile file) { String message = "";
	  
	  if (CSVHelper.hasCSVFormat(file)) { try { fileService.save(file);
	  
	  message = "Uploaded the file successfully: " + file.getOriginalFilename();
	  return ResponseEntity.status(HttpStatus.OK).body(new
	  ResponseMessage(message)); } catch (Exception e) { message =
	  "Could not upload the file: " + file.getOriginalFilename() + "!"; return
	  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
	  ResponseMessage(message)); } }
	  
	  message = "Please upload a csv file!"; return
	  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
	  ResponseMessage(message)); }
	  
	/*
	 * @GetMapping("/customerList") public ResponseEntity<List<Customer>>
	 * getAllCustomersCSV() { try { List<Customer> customers =
	 * fileService.getAllCustomers();
	 * 
	 * if (customers.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 * 
	 * return new ResponseEntity<>(customers, HttpStatus.OK); } catch (Exception e)
	 * { return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */
	 

}
