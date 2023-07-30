package jels.JapaneseELearningSystem.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


public class CourseCreateRequest {
	
	
	private Course course;
	private MultipartFile [] videos;
	
	
	
	
	public Course getCourse() {
		return course;
	}




	public void setCourse(Course course) {
		this.course = course;
	}




	public MultipartFile[] getVideos() {
		return videos;
	}




	public void setVideos(MultipartFile[] videos) {
		this.videos = videos;
	}




	@Override
	public String toString() {
		return "CourseCreateRequest [course=" + course + ", videos=" + Arrays.toString(videos) + "]";
	}
	
	

}
