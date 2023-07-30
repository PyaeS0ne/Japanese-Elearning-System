package jels.JapaneseELearningSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "course_video")
public class CourseVideo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 300, nullable = false)
	private String title;
	
	@Column(length = 300, nullable = false)
	private String path;
	
	@ManyToOne
	@JoinColumn(name = "course_id" ,referencedColumnName = "id" ,nullable = false)
	private Course course;
	
	@Transient
	private String watchedstatus;

}
