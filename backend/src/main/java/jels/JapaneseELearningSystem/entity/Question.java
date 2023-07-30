package jels.JapaneseELearningSystem.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "question_table")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 500, nullable = false)
	private String question;
	
    @OneToMany(  cascade = CascadeType.ALL)
    @JoinColumn(name="question_id")
    private List<AnswerOption> answerOptions = new ArrayList<>();
	
    
	@ManyToOne
	@JoinColumn( name = "test_id", referencedColumnName = "id",nullable = false)
	private Test test;
	
	
		

}
