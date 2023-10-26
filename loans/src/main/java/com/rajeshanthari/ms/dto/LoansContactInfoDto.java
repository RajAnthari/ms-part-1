package com.rajeshanthari.ms.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix = "loans")

@Getter
@Setter
@ToString
public class LoansContactInfoDto {

	String message;
	Map<String, String> contactDetails;
	List<String> onCallSupport;

}