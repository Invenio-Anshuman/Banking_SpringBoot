package com.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.entity.Customer;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {

}
 