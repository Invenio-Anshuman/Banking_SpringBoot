package com.training.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "cust_id")
	private Long cust_id;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "address")
	private String address;
	@Column(name = "balance")
	private String balance;
	@Column(name = "phone")
	private String phone;


	  @Override
	  public String toString() {
	    return "Customer [Cust_Id=" + cust_id + ", Name=" + name + ", Email=" + email + ", Address=" + address + ", Phone=" + phone + ", Balance=" + balance +"]";
	  }

}
