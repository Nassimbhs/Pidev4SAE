package tn.esprit.spring;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import tn.esprit.spring.service.StripeService;

import javax.annotation.PostConstruct;
import java.util.Locale;

@SpringBootApplication
public class TravelAppApplication {

	@PostConstruct
	public void setup()
	{
		Stripe.apiKey = "pk_test_51KX1K0E0eqielpuruy9lPD5F6fKFhdjJzjXYQBVQcQ0G6PHS0McLq4R0oXlOGl7Q92uz03GCuS6Y9AvMyHg9FQ96008kqDGUrM";
	}
	public static void main(String[] args) {
		SpringApplication.run(TravelAppApplication.class, args);
	}

	@Bean
	public LocaleResolver getLocaleResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}


}
