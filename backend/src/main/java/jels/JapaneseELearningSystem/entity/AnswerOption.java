package jels.JapaneseELearningSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Table(name = "answer_option")
public class AnswerOption {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private int id;
	
	@Column(nullable = false)
	private String optionText;
	
	
	
	
	@Column(columnDefinition = "boolean", nullable = false)
	private boolean isCorrect;
	
	
	@ToString.Exclude
	@JsonIgnore
	@ManyToOne
	private Question question;


	public String getOptionText() {
		return optionText;
	}


	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}


	public boolean getIsCorrect() {
		return isCorrect;
	}


	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	

	
	

}
