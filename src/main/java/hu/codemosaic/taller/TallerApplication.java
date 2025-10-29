package hu.codemosaic.taller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TallerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerApplication.class, args);
	}

}
