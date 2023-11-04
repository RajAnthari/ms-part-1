package com.rajeshanthari.ms.functions;

import java.util.function.Function;

import com.rajeshanthari.ms.dto.AccountsMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageFunctions {

	Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

	@Bean
	Function<AccountsMessageDto, AccountsMessageDto> email() {
		return accountsMessageDto -> {
			logger.info("sending email with " + accountsMessageDto.toString());
			return accountsMessageDto;
		};
	}

	@Bean
	Function<AccountsMessageDto, Long> sms() {
		return accountsMessageDto -> {
			logger.info("sending sms with " + accountsMessageDto.toString());
			return accountsMessageDto.accountNumber();
		};
	}

}
