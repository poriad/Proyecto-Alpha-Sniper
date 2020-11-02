package org.iesalixar.poriad.utils;

public class UtilsEmail {
	
	static public String confirmationEmail(String confirmationToken) {
		
		String email = "<!DOCTYPE html>\r\n" + 
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
				"        <h5 style=\"color:#1B4DB0;text-align: center; font-size: 15px; padding-top: 30px;opacity: 1;font-weight: bold;\">Confirma el registro pulsando en el siguiente enlace: <br>" + "http://localhost:8082/confirm-account?token=" +confirmationToken +"</h5>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"    </div>\r\n" + 
				"    </div>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		
		return email;
		
	}

}
