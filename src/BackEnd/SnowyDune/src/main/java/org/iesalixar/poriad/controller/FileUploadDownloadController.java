package org.iesalixar.poriad.controller;

import org.iesalixar.poriad.dto.UploadFileResponse;
import org.iesalixar.poriad.service.EmailSenderService;
import org.iesalixar.poriad.service.FileUploadDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("email") String email) {

		logger.info(name + "   " + email);

		MimeMessage mimeMessage = emailSenderService.getJavaMailSender().createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

			String htmlMsg = emailVC;

			htmlMsg = htmlMsg.replace("|&name&|", name);
			htmlMsg = htmlMsg.replace("|&email&|", email);

			helper.setText(htmlMsg, true);
			helper.setTo("pablo.oria1991@gmail.com");
			helper.setSubject("Curriculum Vitae recibido");
			helper.setFrom("alum.poriad@iesalixar.org");
			helper.addAttachment(file.getOriginalFilename(), file);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		emailSenderService.send(mimeMessage);
		return new UploadFileResponse(file.getName());
	}

	@GetMapping("/getFiles")
	public List<String> getFiles() throws IOException {
		return fileUploadDownloadService.getFiles();
	}

	@GetMapping("/downloadFile/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws MalformedURLException {
		Resource resource = fileUploadDownloadService.loadFileAsResource(fileName);

		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}
		
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
