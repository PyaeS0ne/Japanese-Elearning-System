package jels.JapaneseELearningSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Level  {

	@Id
	//@GeneratedValue (strategy =GenerationType.AUTO)
	private int id;

	@Column(length = 50, nullable = false)
	private String name;

}
