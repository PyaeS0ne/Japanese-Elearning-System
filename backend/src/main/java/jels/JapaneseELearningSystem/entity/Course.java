package jels.JapaneseELearningSystem.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;



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
@Builder
public class Course {
	
	
	@Id
	@GeneratedValue (strategy =  GenerationType.AUTO)
	private int id;
	
	@Column(length = 50, nullable = false)
	@NotBlank(message = "required")
	private String title;
	
	@Column(length = 1000, nullable = false)
	@NotBlank
	private String description;
	
	@Column(length = 200, nullable = true)
	
	private String refBook;
	
	@Column(length = 500, nullable = false)
	
	private String imagePath;
	
	@Column(length = 200, nullable = true)
	private String pdfPath;
	
//	@Column(columnDefinition = "ENUM('N5','N4','N3','N2','N1') NOT NULL")
//	@Enumerated(EnumType.STRING)
//	@NotBlank
//	private Level level;
	
	@ManyToOne
	@JoinColumn(name = "level_id", referencedColumnName = "id", nullable = false)
	private Level level;
	
	@Column( nullable = false )
	private int fee;
	
	
	private int enrolledUsers;
	
	@Transient
	private int totalVideos;
	

		
	 
	
}
