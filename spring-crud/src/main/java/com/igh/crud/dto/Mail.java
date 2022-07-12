package com.igh.crud.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

	private String from;
	private String to;
	private String subject;
	private Map<String,Object> model;
	
	private String html;
}
