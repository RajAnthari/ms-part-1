package com.rajeshanthari.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.rajeshanthari.ms.dto.AccountsContactInfoDto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(title = "Account micro service", description = "Eazy bank aaccounts micro service API documentation", version = "v1", contact = @Contact(email = "rajesh@gmail.com", name = "rajesh", url = "rajesh.com"), license = @License(name = "Apache 2.0", url = "https://rajesh.com")), externalDocs = @ExternalDocumentation(description = "API External documentation", url = "http://rajesh.comextern"))
@EnableConfigurationProperties(value = { AccountsContactInfoDto.class })

public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
