package jels.JapaneseELearningSystem.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class RegisterRequest {
	@NotBlank(message = "Name must be filled")
	private String name;

	@Email(message = "Email format is not valid")
	private String email;
	
}
