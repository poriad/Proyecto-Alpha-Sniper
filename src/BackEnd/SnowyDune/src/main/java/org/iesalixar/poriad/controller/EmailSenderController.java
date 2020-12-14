package org.iesalixar.poriad.controller;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.iesalixar.poriad.dto.ContactDto;
import org.iesalixar.poriad.security.repository.UserRepository;
import org.iesalixar.poriad.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emailService")
@CrossOrigin(value = "*")
public class EmailSenderController {

	final static Logger logger = LoggerFactory.getLogger(EmailSenderController.class);

	@Autowired
	private UserRepository userRepository;

	// @Autowired
	// private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Value("${email.sendContact}")
	private String emailContact;

	@PostMapping("/emailContacto")
	public void sendEmailContact(@RequestBody ContactDto contact) {

		MimeMessage mimeMessage = emailSenderService.getJavaMailSender().createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

			String htmlMsg = emailContact;

			htmlMsg = htmlMsg.replace("|&name&|", contact.getName());
			htmlMsg = htmlMsg.replace("|&email&|", contact.getEmail());
			htmlMsg = htmlMsg.replace("|&comment&|", contact.getComment());

			helper.setText(htmlMsg, true);
			helper.setTo("pablo.oria1991@gmail.com");
			helper.setSubject("Se ha recibido un mensaje de un usuario");
			helper.setFrom("alum.poriad@iesalixar.org");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		emailSenderService.send(mimeMessage);

	}

}
