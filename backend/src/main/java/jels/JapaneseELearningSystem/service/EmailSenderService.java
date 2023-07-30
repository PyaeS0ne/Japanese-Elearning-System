package jels.JapaneseELearningSystem.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
	@Autowired
	JavaMailSender mailSender;
	
	public void sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("elearning.jels@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailSender.send(message);
		
		System.out.println("Mail sent successfully...");
	}

	public String generatePassword() {
		String upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower="abcdefghijklmnopqrstuvwxyz";
		String num="0123456789";
		//String specialChars="<>,.?/}]{[+_-)(*!@#$^&=";
		String combination=upper+lower+num;
		int len=8;
		char[] password=new char[len];
		Random r=new Random();
		for(int i=0; i<len; i++) {
			password[i]=combination.charAt(r.nextInt(combination.length()));
		}
		String generatedPassword=new String(password);
		return generatedPassword;
	}
}
