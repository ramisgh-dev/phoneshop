package com.sip.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import com.sip.ams.controllers.PhoneController;
import java.io.File;

@SpringBootApplication
public class AmsApplication extends SpringBootServletInitializer {

	public static void main(String[] args)  {
		new File(PhoneController.uploadDirectory).mkdir();
		SpringApplication.run(AmsApplication.class, args);

	}

}
