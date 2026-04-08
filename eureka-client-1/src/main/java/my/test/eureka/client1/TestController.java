package my.test.eureka.client1;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Value("${spring.application.name}")
	private String applicationName;

	@GetMapping("/test")
	public ResponseEntity<String> test(HttpServletRequest request) {
		String requestorIp = resolveRequestorIp(request);
		log.info("controller enter | application={} requestorIp={}", applicationName, requestorIp);
		log.info("before response | application={} requestorIp={}", applicationName, requestorIp);
		return ResponseEntity.ok("OK");
	}

	private static String resolveRequestorIp(HttpServletRequest request) {
		String forwarded = request.getHeader("X-Forwarded-For");
		if (forwarded != null && !forwarded.isBlank()) {
			return forwarded.split(",")[0].strip();
		}
		return request.getRemoteAddr();
	}

}
