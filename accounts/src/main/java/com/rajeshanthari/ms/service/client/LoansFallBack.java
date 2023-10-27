package com.rajeshanthari.ms.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rajeshanthari.ms.dto.LoansDto;

@Component
public class LoansFallBack implements LoansFeignClient {
	@Override
	public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
		return null;
	}
}
