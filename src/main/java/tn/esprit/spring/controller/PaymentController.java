package tn.esprit.spring.controller;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entity.Car;
import tn.esprit.spring.entity.Response;
import tn.esprit.spring.service.CarService;
import tn.esprit.spring.service.StripeService;

import javax.websocket.server.PathParam;

@Controller
public class PaymentController {

	@Autowired
	CarService carService ;
	@Value("${stripe.key.public}")
	private String API_PUBLIC_KEY;

	private StripeService stripeService;

	public PaymentController(StripeService stripeService) {
		this.stripeService = stripeService;
	}

	@GetMapping("/charge/{idcar}")
	public String chargePage(Model model, @PathVariable("idcar") int idcar){
		Car car = carService.getCarById(idcar);
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		model.addAttribute("priceUSD",car.getPrice());
		model.addAttribute("price",car.getPrice());
		return "charge";
	}

	@PostMapping("/create-charge/{price}")
	public @ResponseBody Response createCharge(String email, String token,@PathVariable("price") int price) throws StripeException {

		if (token == null) {
			return new Response(false, "Stripe payment token is missing. please try again later.");
		}

		System.out.println(" i'm here");
		System.out.println("This is the price : "+price);
		int a= price*100 ;
		String chargeId = stripeService.createCharge(email, token, a);// 999 usd
		System.out.println("Charge id = "+chargeId);
		System.out.println("Charge email = "+email);

		if (chargeId == null) {
			return new Response(false, "An error accurred while trying to charge.");
		}

		// You may want to store charge id along with order information

		return new Response(true, "Success your charge id is " + chargeId);
	}
}
