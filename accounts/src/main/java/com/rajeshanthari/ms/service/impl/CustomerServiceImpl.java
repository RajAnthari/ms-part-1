package com.rajeshanthari.ms.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rajeshanthari.ms.domain.Accounts;
import com.rajeshanthari.ms.domain.Customer;
import com.rajeshanthari.ms.dto.AccountsDto;
import com.rajeshanthari.ms.dto.CardsDto;
import com.rajeshanthari.ms.dto.CustomerDetailsDto;
import com.rajeshanthari.ms.dto.LoansDto;
import com.rajeshanthari.ms.exception.ResourceNotFoundException;
import com.rajeshanthari.ms.mapper.AccountsMapper;
import com.rajeshanthari.ms.mapper.CustomerMapper;
import com.rajeshanthari.ms.repository.AccountsRepository;
import com.rajeshanthari.ms.repository.CustomerRepository;
import com.rajeshanthari.ms.service.ICustomerService;
import com.rajeshanthari.ms.service.client.CardsFeignClient;
import com.rajeshanthari.ms.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	private CardsFeignClient cardsFeignClient;
	private LoansFeignClient loansFeignClient;

	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
		CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
				new CustomerDetailsDto());
		customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));

		ResponseEntity<CardsDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
		if (cardDtoResponseEntity != null) {
			customerDetailsDto.setCardsDto(cardDtoResponseEntity.getBody());
		}

		ResponseEntity<LoansDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
		if (loanDtoResponseEntity != null) {
			customerDetailsDto.setLoansDto(loanDtoResponseEntity.getBody());
		}

		return customerDetailsDto;
	}

}
