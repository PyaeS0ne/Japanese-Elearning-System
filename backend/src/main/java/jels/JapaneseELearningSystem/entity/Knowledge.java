package jels.JapaneseELearningSystem.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Knowledge {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 200, nullable = false)
	@NotBlank
	private String title;
	
	@Column(nullable = false)
	private String featureImage;
	
	@Column(length = 500, nullable = false)
	@NotBlank
	private String intro;

	@Column(length = 100, nullable = false)
	private String contentPath;
	
	@Column(length = 100, nullable = true)
	private String tag;
	
	@Column(length = 100)
	private LocalDate date;
	
//	@OneToMany(mappedBy = "knowledge", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JsonManagedReference
//	private List<KnowledgeImage> image;
//	
	
	

}
