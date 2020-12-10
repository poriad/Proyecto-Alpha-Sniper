package org.iesalixar.poriad.controller;


import org.iesalixar.poriad.dto.UploadFileResponse;
import org.iesalixar.poriad.service.EmailSenderService;
import org.iesalixar.poriad.service.FileUploadDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/files")
public class FileUploadDownloadController {

	 private static final Logger logger = LoggerFactory.getLogger(FileUploadDownloadController.class);

	    @Autowired
	    private FileUploadDownloadService fileUploadDownloadService;

	    @Autowired
	    private EmailSenderService emailSenderService;
	    
	    @Value("${email.sendCV}")
	    private String emailVC;
	    
	    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("name") String name, @RequestParam("email") String email) {
	       
	        logger.info(name + "   " + email);
	        
	        MimeMessage mimeMessage = emailSenderService.getJavaMailSender().createMimeMessage();
	    	MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(mimeMessage,true, "utf-8");
				
				String htmlMsg = emailVC;
				
				htmlMsg = htmlMsg.replace("|&name&|", name);
				
			/*
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
	    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">Hemos recibido un mensaje del formulario de contacto de "+ name
	    			+"</h5>\r\n" + 
	    			"\r\n" + 
	    			"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 17px; padding-top: 30px;opacity: 1;font-weight: bold;\">Con el siguiente mensaje:</h5>\r\n" +  
	    			"		<p style=\"color:black;text-align: center; font-size: 16px; padding-top: 30px;opacity: 1;font-weight: bold;\">" + name + "</p>\r\n" + 
	    			"		<p style=\"color:#1B4DB0;text-align: center; font-size: 16px; padding-top: 30px;opacity: 1;font-weight: bold;\">Su email de contacto es: " + email + "</p>\r\n" + 
	    			"    </div>\r\n" + 
	    			"    </div>\r\n" + 
	    			"</body>\r\n" + 
	    			"</html>";
	    	//mimeMessage.setContent(htmlMsg, "text/html");
	    */
				
				
				helper.setText(htmlMsg, true);
				helper.setTo("pablo.oria1991@gmail.com");
				helper.setSubject("Contacto procedente de un usuario recibido");
		    	helper.setFrom("alum.poriad@iesalixar.org");
		        helper.addAttachment(file.getName(), file);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	    	
	    	emailSenderService.send(mimeMessage);
	        return new UploadFileResponse(file.getName());
	    }

	    // Displays the list of uploaded files.
	    @GetMapping("/getFiles")
	    public List<String> getFiles() throws IOException {
	        return fileUploadDownloadService.getFiles();
	    }

	    // Downloads a file using filename.
	    @GetMapping("/downloadFile/{fileName}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws MalformedURLException {
	        Resource resource = fileUploadDownloadService.loadFileAsResource(fileName);
	        // Try to determine file's content type
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            logger.info("Could not determine file type.");
	        }
	        // Fallback to the default content type if type could not be determined
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }
	    
}
