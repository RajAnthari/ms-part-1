package com.rajeshanthari.ms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rajeshanthari.ms.dto.CustomerDetailsDto;
import com.rajeshanthari.ms.dto.ErrorResponseDto;
import com.rajeshanthari.ms.service.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@Tag(name = "Rest API's for Customers in Bank", description = "Rest API's in Bank to FETCH customer details")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private ICustomerService customerService;

	public CustomerController(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@Operation(summary = "Fetch Customer Details REST API", description = "REST API to fetch Customer details based on a mobile number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/cutomer-details")
	public ResponseEntity<CustomerDetailsDto> fetchAccountDetails(
			@RequestHeader("eazybank-correlation-id") String eazybankCorrelationId,
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits") String mobileNumber) {
		CustomerDetailsDto customerDto = customerService.fetchCustomerDetails(mobileNumber, eazybankCorrelationId);
		logger.debug("CustomerController eazybank-correlation-id found:{}", eazybankCorrelationId);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

}
