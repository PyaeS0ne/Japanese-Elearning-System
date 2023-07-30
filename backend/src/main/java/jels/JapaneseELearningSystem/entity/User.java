package jels.JapaneseELearningSystem.entity;


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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
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
public class User {
	
	@Id
	@GeneratedValue (strategy =  GenerationType.AUTO)
	private int id;
	
	@Column(length = 30, nullable = false)
	@NotBlank(message = "name is required")
	private String name;
	
	@Column(length = 100, nullable = false, unique = true)
	@NotBlank(message = "email is required")
	//@Email (message = "please enter valid email")
	private String email;
	
	@Column(length = 8, nullable = false)
	@NotBlank(message = "password is required")
	private String password;
	
	@Column(columnDefinition = "ENUM('admin','user') NOT NULL")
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@Column(nullable = true)
	private String stripe_customer_id;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  name = "enrolled_course", 
	  joinColumns = @JoinColumn (name = "user_id"), 
	  inverseJoinColumns =  @JoinColumn (name = "course_id"))
	private List<Course> enrolled_course;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(
	  name = "watched_video", 
	  joinColumns = @JoinColumn (name = "user_id"), 
	  inverseJoinColumns =  @JoinColumn (name = "video_id"))
	private List<CourseVideo> watched_video;
	
	
	

}
