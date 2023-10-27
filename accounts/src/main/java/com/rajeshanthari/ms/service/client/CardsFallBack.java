package com.rajeshanthari.ms.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rajeshanthari.ms.dto.CardsDto;

@Component
public class CardsFallBack implements CardsFeignClient {
	@Override
	public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
		return null;
	}
}
