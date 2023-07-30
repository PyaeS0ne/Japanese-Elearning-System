package jels.JapaneseELearningSystem.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.param.PaymentMethodListParams.Type;

import antlr.Token;

@Service
public class StripeService {

	@Value("${stripe.secret.key}")
	private String stripeSecretKey;

	@PostConstruct
	public void init() {
		Stripe.apiKey = stripeSecretKey;
	}

	public List<PaymentMethod> getCards(String customerId) {

		PaymentMethodListParams params = PaymentMethodListParams.builder().setCustomer(customerId).setType(Type.CARD)
				.build();
		try {
			return PaymentMethod.list(params).getData();
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public PaymentIntent payAndSaveCard(Customer customer, long amount) {

		try {

			PaymentIntentCreateParams params2 = PaymentIntentCreateParams.builder().setCustomer(customer.getId())
					.setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION).setCurrency("mmk")
					.setAmount(amount)

					.addPaymentMethodType("card").build();
			return PaymentIntent.create(params2);

		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	public boolean deleteCard(String card_id) {
		
		try {
			PaymentMethod paymentMethod=PaymentMethod.retrieve(card_id);
			PaymentMethod updatedPaymentMethod=paymentMethod.detach();
			
			if(updatedPaymentMethod!=null) {
				return true;
			}
			return  false;
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
		}
	}

	public StripeService() {
		super();
		Stripe.apiKey = stripeSecretKey;
	}

	public PaymentIntent pay(long amount) throws StripeException {
		try {
			
			PaymentIntentCreateParams params=PaymentIntentCreateParams.builder()
					
					
					.setCurrency("mmk")
					.setAmount(amount)
					.addPaymentMethodType("card")
					.build();
			return PaymentIntent.create(params);
		
			
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public PaymentIntent createPaymentIntent(PaymentIntentCreateParams params) throws StripeException {
		
			return PaymentIntent.create(params);
		
	}
	
	public boolean charge(long amount,String customer_id,String paymentMethodId) {
		PaymentIntentCreateParams params =
				  PaymentIntentCreateParams.builder()
				    .setCurrency("mmk")
				    .setAmount(amount)
				   
				    .setPaymentMethod(paymentMethodId)
				    .setCustomer(customer_id)
				    .setConfirm(true)
				    .setOffSession(true)
				    .build();
				try {
				
				   PaymentIntent paymentIntent= PaymentIntent.create(params);
				   System.out.println("return paymentInent :"+paymentIntent);
				  return true;
				} catch (StripeException e) {
				  // Error code will be authentication_required if authentication is needed
				  System.out.println("Error code is : " +e.getMessage());
				  return false;
				  
				}
	}
}
	
//	public String charge(long amount, String token) {
//		// long amount=0;
//		String cardToken = "";
//		String currency = "mmk";
//		Charge charge;
//		System.out.println("amount : " + amount + "token : " + token);
//		try {
//			ChargeCreateParams chargeParams = ChargeCreateParams.builder().setAmount(amount).setCurrency(currency)
//					.setSource(token).build();
//
//			charge = Charge.create(chargeParams);
//
//		} catch (StripeException e) {
//			System.out.println(e.getMessage());
//			// TODO: handle exception
//			return null;
//		}
//		System.out.println(charge.getReceiptUrl());
//		return charge.getReceiptUrl();
//	}



