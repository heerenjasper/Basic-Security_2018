package be.pxl.AON11.basicsecurity;

import be.pxl.AON11.basicsecurity.config.JpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(JpaConfig.class)
@SpringBootApplication(scanBasePackages = "be.pxl.AON11.basicsecurity")
public class BasicSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicSecurityApplication.class, args);
	}
}
