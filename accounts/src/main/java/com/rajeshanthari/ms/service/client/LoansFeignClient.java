package com.rajeshanthari.ms.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rajeshanthari.ms.dto.LoansDto;

@FeignClient("loans")
public interface LoansFeignClient {
	@GetMapping(value = "/api/fetch", consumes = "application/json")
	public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);
}
