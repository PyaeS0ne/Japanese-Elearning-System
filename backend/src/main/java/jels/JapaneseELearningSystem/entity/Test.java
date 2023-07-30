package jels.JapaneseELearningSystem.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 100, nullable = false)
	@NotBlank
	private String title;
	
	@Column(columnDefinition = "boolean", nullable = false)
	@ColumnDefault("false")
	private boolean published;

	@ManyToOne
	@JoinColumn(name = "level_id", referencedColumnName = "id", nullable = false)
	private Level level;
	
	
	
	

}
