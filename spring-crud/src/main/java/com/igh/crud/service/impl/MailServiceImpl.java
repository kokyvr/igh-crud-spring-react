package com.igh.crud.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.igh.crud.dto.Mail;
import com.igh.crud.service.MailService;

import freemarker.template.Configuration;

@Service
public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private Configuration configuration;
	
	@Override
	public String getContentFromTemplate(Map<String, Object> model, String html) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(html), model));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	@Override
	public void SendEmail(Mail mail) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			

			helper.setTo(mail.getTo());
			helper.setText(getContentFromTemplate(mail.getModel(),mail.getHtml()),true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom());
			emailSender.send(message);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
		
	}

}
