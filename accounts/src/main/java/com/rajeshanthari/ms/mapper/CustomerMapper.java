package com.rajeshanthari.ms.mapper;

import com.rajeshanthari.ms.domain.Customer;
import com.rajeshanthari.ms.dto.CustomerDetailsDto;
import com.rajeshanthari.ms.dto.CustomerDto;

public class CustomerMapper {

	public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
		customerDto.setName(customer.getName());
		customerDto.setEmail(customer.getEmail());
		customerDto.setMobileNumber(customer.getMobileNumber());
		return customerDto;
	}

	public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		customer.setMobileNumber(customerDto.getMobileNumber());
		return customer;
	}

	public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
		customerDetailsDto.setName(customer.getName());
		customerDetailsDto.setEmail(customer.getEmail());
		customerDetailsDto.setMobileNumber(customer.getMobileNumber());
		return customerDetailsDto;
	}

}