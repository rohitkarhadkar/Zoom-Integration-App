package dev.rohit.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ZoomIntegrationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoomIntegrationAppApplication.class, args);
	}

	@GetMapping("/")
	public ResponseEntity<String> connectZoom() {
		return ResponseEntity.ok("Hello World!");
	}
}
