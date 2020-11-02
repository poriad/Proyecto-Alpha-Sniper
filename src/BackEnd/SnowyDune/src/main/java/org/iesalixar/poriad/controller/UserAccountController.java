package org.iesalixar.poriad.controller;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.iesalixar.poriad.entity.ConfirmationToken;
import org.iesalixar.poriad.entity.UserSnowy;
import org.iesalixar.poriad.repository.ConfirmationTokenRepository;
import org.iesalixar.poriad.repository.UserRepository;
import org.iesalixar.poriad.service.EmailSenderService;
import org.iesalixar.poriad.utils.UtilsEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAccountController {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    
    // Prueba Email BORRAR
    @RequestMapping(value="/pruebaEmail", method = RequestMethod.POST)
    public ModelAndView sendEmailHTML(ModelAndView modelAndView) {
    	
    	
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
    	
    	
    	
        modelAndView.setViewName("register");
        return modelAndView;
    }
    
    
    @RequestMapping(value="/registerForm", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, UserSnowy user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value="/registerForm", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, UserSnowy user)
    {

        UserSnowy existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        
        if(existingUser != null)
        {
            modelAndView.addObject("message","This email already exists!");
            modelAndView.setViewName("error");
        }
        else
        {	
        	userRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);
            MimeMessage mimeMessage = emailSenderService.getJavaMailSender().createMimeMessage();
        	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        	String htmlMsg = UtilsEmail.confirmationEmail(confirmationToken.getConfirmationToken());

        	try {
    			helper.setText(htmlMsg, true);
    			helper.setTo(user.getEmail());
    			helper.setSubject("Confirmación de Registro - SnowyDune");
    	    	helper.setFrom("alum.poriad@iesalixar.org");
    	    	
    		} catch (MessagingException e) {
    			e.printStackTrace();
    		}
        	
        	emailSenderService.send(mimeMessage);
            
            
            /* BORRAR
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("alum.poriad@iesalixar.org");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);*/

            modelAndView.addObject("email", user.getEmail());

            modelAndView.setViewName("successfulRegistration");
        }

        return modelAndView;
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null) {
            UserSnowy user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        } else {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
	
}
