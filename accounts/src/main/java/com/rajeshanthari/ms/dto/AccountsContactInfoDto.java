package com.rajeshanthari.ms.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
@ToString
public class AccountsContactInfoDto {

	String message;
	Map<String, String> contactDetails;
	List<String> onCallSupport;

}