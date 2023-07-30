package jels.JapaneseELearningSystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.*;



import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.service.StripeService;
import jels.JapaneseELearningSystem.service.UserService;

@RestController
public class PaymentController {
	@Autowired
	UserService userService;

	@Autowired
	private StripeService stripeService;

//	@PostMapping("/payment/save_and_charge")
//	public ResponseEntity<?> saveAndCharge(@RequestParam("amount") long amount, @RequestParam("cardId") int id){
//		System.out.println("amount : "+amount+ ", cardid : "+ id);
//		
//		UserCard card= userCardService.getById(id);
//		String token= card.getTokenId();
//		
//		String receivedUrl= stripeService.charge(amount, token);
//		if (receivedUrl==null) {
//			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
//		}
//		return ResponseEntity.ok(receivedUrl);
//	}
//	
	@PostMapping("/payment/charge")
	public ResponseEntity<?> charge(@RequestParam("amount") long amount, @RequestParam("user_id") int user_id
			,@RequestParam("paymentMethodId") String paymentMethodId
			) {
		
		
		User user=userService.getById(user_id);
		System.out.println("amount "+amount+ "customer id "+ user.getStripe_customer_id()+ "pm id :"+paymentMethodId);
		
		boolean success=stripeService.charge(amount, user.getStripe_customer_id(), paymentMethodId);
		if(success) {
			return ResponseEntity.ok(success);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
	}
	@DeleteMapping("/payment/delete_card")
	public ResponseEntity<?> deleteCard(@RequestParam("card_id") String card_id
			) {
		
		boolean deleted=stripeService.deleteCard(card_id);
		
		if(deleted) {
			return ResponseEntity.ok(deleted);
		}
		return new ResponseEntity<>(deleted, HttpStatus.BAD_REQUEST);
		
		
		
		
	}

//	@PostMapping("/payment/save_card")
//	public ResponseEntity<?> saveCard(@RequestBody UserCard card){
//		System.out.println(card);
//		UserCard saved_card=userCardService.addCard(card);
//		
//	if(saved_card!=null) {
//		return ResponseEntity.ok(saved_card);
//	}else 
//		return ResponseEntity.badRequest().build();
//		
//	}

//	@PostMapping("/create-payment-intent/{amount}")
//	public ResponseEntity<?> createPaymentIntent(
//			// @RequestBody PaymentRequest paymentRequest
//			@PathVariable long amount) {
//		System.out.println("berfor");
////		long amount=paymentRequest.getAmount();
////		String mail=paymentRequest.getMail();		
////		System.out.println();
//		try {
//			// stripeInitializer.init();
//
//			// Long amount =1000L;
//
//			PaymentIntentCreateParams parmas = new PaymentIntentCreateParams.Builder().setCurrency("mmk")
//					.setAmount(amount)
//					// .setReceiptEmail("elearning.jels@gmail.com")
//
//					.build();
//
//			PaymentIntent paymentIntent = stripeService.createPaymentIntent(parmas);
//			// HashMap<String, String> client= new HashMap<>();
//			// paymentIntent.put("clientSecret", paymentIntent.getClientSecret());
//			String clientSecret = paymentIntent.getClientSecret();
//
//			System.out.println(paymentIntent);
//			// return null;
//			return ResponseEntity.ok(clientSecret);
//
//		} catch (StripeException e) {
////			HashMap<String, Object> errorReturn= new HashMap<>();
////			errorReturn.put("error", e.getMessage());
//
//			System.out.println(ResponseEntity.badRequest().body(e.getMessage()));
//			// return ResponseEntity.badRequest().body(e.getMessage());
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//	}

	@GetMapping("/payment/cardList")
	public ResponseEntity<?> getCardList(@RequestParam("userId") int userId) {
		System.out.println("jere");
		String customerId = userService.getById(userId).getStripe_customer_id();
		List<PaymentMethod> cardList = stripeService.getCards(customerId);
		if (cardList.size() < 1) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		System.out.println("user has cards");
		return ResponseEntity.ok(cardList);

	}

	@PostMapping("/payment/saveAndPay")
	public ResponseEntity<?> saveAndPay(@RequestParam("userId") int userId, @RequestParam("amount") long amount,
			@RequestParam("saveCard") boolean saveCard) {

		if (saveCard) {//----when saveCard is true----(pay and save card for future payment)
			try {
				System.out.println("savecard"+saveCard);

				User user = userService.getById(userId);
				Customer customer;
				if (user.getStripe_customer_id() == null) {
					System.out.println("user dose not have stripe customer id");
					
					CustomerCreateParams params = CustomerCreateParams.builder()
							.setEmail("phyopyae261@gmail.com")

							.build();

					customer = Customer.create(params);
					user.setStripe_customer_id(customer.getId());
					userService.update(user);
				} else {
					System.out.println("user have stripe customer id");
					customer = Customer.retrieve(user.getStripe_customer_id());
					
				}

				PaymentIntent paymentIntent = stripeService.payAndSaveCard(customer, amount);

				if (paymentIntent == null) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				return ResponseEntity.ok(paymentIntent.getClientSecret());

			} catch (StripeException e) {

				System.out.println(e.getMessage());
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

		} 
		
		else {//-------when saveCard is false--------(pay without saving card) 		
			try {
				System.out.println("savecard"+saveCard);

				PaymentIntent paymentIntent= stripeService.pay(amount);
				if (paymentIntent == null) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				return ResponseEntity.ok(paymentIntent.getClientSecret());
				
			} catch (StripeException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

	}

}
