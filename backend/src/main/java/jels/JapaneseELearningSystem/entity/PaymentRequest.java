package jels.JapaneseELearningSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PaymentRequest {
	
	
	private long amount;
	private String mail;
	

}
