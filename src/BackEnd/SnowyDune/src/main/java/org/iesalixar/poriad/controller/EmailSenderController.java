package org.iesalixar.poriad.controller;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.iesalixar.poriad.dto.ContactDto;
import org.iesalixar.poriad.dto.CurriculumContact;
import org.iesalixar.poriad.security.repository.UserRepository;
import org.iesalixar.poriad.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/emailService")
@CrossOrigin(value = "*")
public class EmailSenderController {

	final static Logger logger = LoggerFactory.getLogger(EmailSenderController.class);
	
	@Autowired
    private UserRepository userRepository;

    //@Autowired
    //private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;
    
    @PostMapping("/pruebaEmail")
    public void sendEmailHTML() {
    	
    	
    	MimeMessage mimeMessage = emailSenderService.getJavaMailSender().createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	String htmlMsg = "<!DOCTYPE html>\r\n" + 
    			"<html lang=\"en\">\r\n" + 
    			"<head>\r\n" + 
    			"    <meta charset=\"UTF-8\">\r\n" + 
    			"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
    			"    <title>Document</title>\r\n" + 
    			"\r\n" + 
    			"    <style>\r\n" + 
    			"\r\n" + 
    			"        .hola {\r\n" + 
    			"            background-image: url(\"https://wallpapercave.com/wp/wp4020827.jpg\");\r\n" + 
    			"            background-color: #cccccc;\r\n" + 
    			"            background-repeat: no-repeat;\r\n" + 
    			"            background-size: cover;\r\n" + 
    			"            height: 500px;\r\n" + 
    			"            width: 800px;\r\n" + 
    			"            }\r\n" + 
    			"\r\n" + 
    			"    </style>\r\n" + 
    			"\r\n" + 
    			"</head>\r\n" + 
    			"<body>\r\n" + 
    			"    <div class=\"hola\">\r\n" + 
    			"        <div style=\"background-color: #ffffff;\r\n" + 
    			"        height: 100%;\r\n" + 
    			"        position: absolute;\r\n" + 
    			"        top: 20%;\r\n" + 
    			"        left: 17%;\r\n" + 
    			"        border-bottom-color: #1B4DB0;\r\n" + 
    			"        opacity: 0.80;\"\r\n" + 
    			"        >\r\n" + 
    			"        <h1 class=\"title\" style=\"text-align: center;color: #1B4DB0; opacity: 1; padding-top: 80px; font-size: 30px;font-weight: bold;\" >S n o w y D u n e</h1>\r\n" + 
    			"        <p style=\"line-height:20%; color:#1B4DB0; margin:0px 0px 0px 0px; padding:0px 0px 0px 0px;text-align: center;opacity: 1;font-weight: bold;\">........................................................................................................................................</p>\r\n" + 
    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">¡Gracias por registrarte!</h5>\r\n" + 
    			"\r\n" + 
    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">Confirma el registro pulsando en el siguiente enlace: </h5>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"    </div>\r\n" + 
    			"    </div>\r\n" + 
    			"</body>\r\n" + 
    			"</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    	try {
			helper.setText(htmlMsg, true);
			helper.setTo("pablo.oria1991@gmail.com");
			helper.setSubject("Último Spam!");
	    	helper.setFrom("alum.poriad@iesalixar.org");
	    	
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	
    	emailSenderService.send(mimeMessage);
    	
    }
    
    
    @PostMapping("/emailContacto")
    public void sendEmailContact(@RequestBody ContactDto contact) {
    	
    	
    	MimeMessage mimeMessage = emailSenderService.getJavaMailSender().createMimeMessage();
    	MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage,true, "utf-8");
		
    	String htmlMsg = "<!DOCTYPE html>\r\n" + 
    			"<html lang=\"en\">\r\n" + 
    			"<head>\r\n" + 
    			"    <meta charset=\"UTF-8\">\r\n" + 
    			"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
    			"    <title>Document</title>\r\n" + 
    			"\r\n" + 
    			"    <style>\r\n" + 
    			"\r\n" + 
    			"        .hola {\r\n" + 
    			"            background-image: url(\"https://wallpapercave.com/wp/wp4020827.jpg\");\r\n" + 
    			"            background-color: #cccccc;\r\n" + 
    			"            background-repeat: no-repeat;\r\n" + 
    			"            background-size: cover;\r\n" + 
    			"            height: 600px;\r\n" + 
    			"            width: 800px;\r\n" + 
    			"            }\r\n" + 
    			"\r\n" + 
    			"    </style>\r\n" + 
    			"\r\n" + 
    			"</head>\r\n" + 
    			"<body>\r\n" + 
    			"    <div class=\"hola\">\r\n" + 
    			"        <div style=\"background-color: #ffffff;\r\n" + 
    			"        height: 100%;\r\n" + 
    			"        position: absolute;\r\n" + 
    			"        top: 20%;\r\n" + 
    			"        left: 17%;\r\n" + 
    			"        border-bottom-color: #1B4DB0;\r\n" + 
    			"        opacity: 0.80;\"\r\n" + 
    			"        >\r\n" + 
    			"        <h1 class=\"title\" style=\"text-align: center;color: #1B4DB0; opacity: 1; padding-top: 80px; font-size: 30px;font-weight: bold;\" >S n o w y D u n e</h1>\r\n" + 
    			"        <p style=\"line-height:20%; color:#1B4DB0; margin:0px 0px 0px 0px; padding:0px 0px 0px 0px;text-align: center;opacity: 1;font-weight: bold;\">........................................................................................................................................</p>\r\n" + 
    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">Hemos recibido un mensaje del formulario de contacto de "+ contact.getName()
    			+"</h5>\r\n" + 
    			"\r\n" + 
    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">Con el siguiente mensaje:</h5>\r\n" +  
    			"		<p style=\"color:black;text-align: center; font-size: 16px; padding-top: 30px;opacity: 1;font-weight: bold;\">" + contact.getComment() + "</p>\r\n" + 
    			"		<p style=\"color:#1B4DB0;text-align: center; font-size: 16px; padding-top: 30px;opacity: 1;font-weight: bold;\">Su email de contacto es: " + contact.getEmail() + "</p>\r\n" + 
    			"    </div>\r\n" + 
    			"    </div>\r\n" + 
    			"</body>\r\n" + 
    			"</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    
			helper.setText(htmlMsg, true);
			helper.setTo("pablo.oria1991@gmail.com");
			helper.setSubject("Contacto procedente de un usuario recibido");
	    	helper.setFrom("alum.poriad@iesalixar.org");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	
    	emailSenderService.send(mimeMessage);
    	
    }
    
    @PostMapping("/emailCV")
    public void sendEmailCV(@RequestBody CurriculumContact contact) {
    	
    	MimeMessage mimeMessage = emailSenderService.getJavaMailSender().createMimeMessage();
    	MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage,true, "utf-8");
		
    	String htmlMsg = "<!DOCTYPE html>\r\n" + 
    			"<html lang=\"en\">\r\n" + 
    			"<head>\r\n" + 
    			"    <meta charset=\"UTF-8\">\r\n" + 
    			"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
    			"    <title>Document</title>\r\n" + 
    			"\r\n" + 
    			"    <style>\r\n" + 
    			"\r\n" + 
    			"        .hola {\r\n" + 
    			"            background-image: url(\"https://wallpapercave.com/wp/wp4020827.jpg\");\r\n" + 
    			"            background-color: #cccccc;\r\n" + 
    			"            background-repeat: no-repeat;\r\n" + 
    			"            background-size: cover;\r\n" + 
    			"            height: 600px;\r\n" + 
    			"            width: 800px;\r\n" + 
    			"            }\r\n" + 
    			"\r\n" + 
    			"    </style>\r\n" + 
    			"\r\n" + 
    			"</head>\r\n" + 
    			"<body>\r\n" + 
    			"    <div class=\"hola\">\r\n" + 
    			"        <div style=\"background-color: #ffffff;\r\n" + 
    			"        height: 100%;\r\n" + 
    			"        position: absolute;\r\n" + 
    			"        top: 20%;\r\n" + 
    			"        left: 17%;\r\n" + 
    			"        border-bottom-color: #1B4DB0;\r\n" + 
    			"        opacity: 0.80;\"\r\n" + 
    			"        >\r\n" + 
    			"        <h1 class=\"title\" style=\"text-align: center;color: #1B4DB0; opacity: 1; padding-top: 80px; font-size: 30px;font-weight: bold;\" >S n o w y D u n e</h1>\r\n" + 
    			"        <p style=\"line-height:20%; color:#1B4DB0; margin:0px 0px 0px 0px; padding:0px 0px 0px 0px;text-align: center;opacity: 1;font-weight: bold;\">........................................................................................................................................</p>\r\n" + 
    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">Hemos recibido un mensaje del formulario de contacto de "+ contact.getName()
    			+"</h5>\r\n" + 
    			"\r\n" + 
    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">Con el siguiente mensaje:</h5>\r\n" +  
    			"		<p style=\"color:black;text-align: center; font-size: 16px; padding-top: 30px;opacity: 1;font-weight: bold;\">" + contact.getName() + "</p>\r\n" + 
    			"		<p style=\"color:#1B4DB0;text-align: center; font-size: 16px; padding-top: 30px;opacity: 1;font-weight: bold;\">Su email de contacto es: " + contact.getEmail() + "</p>\r\n" + 
    			"    </div>\r\n" + 
    			"    </div>\r\n" + 
    			"</body>\r\n" + 
    			"</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    
			helper.setText(htmlMsg, true);
			helper.setTo("pablo.oria1991@gmail.com");
			helper.setSubject("Contacto procedente de un usuario recibido");
	    	helper.setFrom("alum.poriad@iesalixar.org");
	   
	    	String fileDirectory = "./Files/" + contact.getFilename();
	    	logger.info(fileDirectory);
	    	ClassPathResource classPathResource = new ClassPathResource(fileDirectory);
	        helper.addAttachment(classPathResource.getFilename(), classPathResource);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	
    	emailSenderService.send(mimeMessage);
    	
    }
}
