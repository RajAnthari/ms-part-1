package com.rajeshanthari.ms.service;

import com.rajeshanthari.ms.dto.CustomerDetailsDto;

public interface ICustomerService {

	/**
	 *
	 * @param mobileNumber - Input Mobile Number
	 * @return Customer Details based on a given mobileNumber
	 */
	CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
