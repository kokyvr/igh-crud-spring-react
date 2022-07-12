package com.igh.crud.service;

import java.util.Map;

import com.igh.crud.dto.Mail;


public interface MailService {

	public String getContentFromTemplate(Map<String,Object> model,String html);
	
	public void SendEmail(Mail mail);
	
}
