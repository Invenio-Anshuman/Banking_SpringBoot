package com.training.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.training.entity.Customer;
import com.training.helper.CSVHelper;
import com.training.repository.CustomerRepository;

@Service
public class CSVService {
  @Autowired
  CustomerRepository repository;

  public void save(MultipartFile file) {
    try {
      List<Customer> customers = CSVHelper.csvToCustomers(file.getInputStream());
      repository.saveAll(customers);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public List<Customer> getAllCustomers() {
    return repository.findAll();
  }
}