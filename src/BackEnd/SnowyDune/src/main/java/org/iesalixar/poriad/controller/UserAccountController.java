package org.iesalixar.poriad.controller;

import org.iesalixar.poriad.dao.ConfirmationTokenRepository;
import org.iesalixar.poriad.dao.UserRepository;
import org.iesalixar.poriad.entity.ConfirmationToken;
import org.iesalixar.poriad.entity.UserSnowy;
import org.iesalixar.poriad.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    
    @RequestMapping("/HelloWorld")
	public ModelAndView firstPage() {
		return new ModelAndView("register");
	}
    
    @RequestMapping(value="/registerForm", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, UserSnowy user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registerForm");
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

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("chand312902@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("emailId", user.getEmail());

            modelAndView.setViewName("successfulRegisteration");
        }

        return modelAndView;
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            UserSnowy user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
	
}
