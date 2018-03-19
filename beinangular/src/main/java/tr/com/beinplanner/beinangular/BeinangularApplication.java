package tr.com.beinplanner.beinangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages={"tr.com.beinplanner"})
@SpringBootApplication
public class BeinangularApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeinangularApplication.class, args);
	}
}
