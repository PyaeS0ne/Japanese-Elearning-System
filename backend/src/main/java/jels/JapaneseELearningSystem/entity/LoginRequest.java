package jels.JapaneseELearningSystem.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class LoginRequest {	
	@Email(message = "Email format is not valid")
	private String email;
	
	@NotBlank(message = "Password must be filled")
	private String password;
	


}
