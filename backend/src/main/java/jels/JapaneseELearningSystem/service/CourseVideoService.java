package jels.JapaneseELearningSystem.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.CourseVideo;
import jels.JapaneseELearningSystem.entity.Level;

public interface CourseVideoService {
	
	public CourseVideo create(CourseVideo CourseVideo);
	public CourseVideo update(int video_id,CourseVideo CourseVideo);
	public List<CourseVideo> getAllCourseVideo();
	
	public CourseVideo getCourseVideo(int id);
	
	public List<CourseVideo> getAllByCourseId(int course_id);
    
	public boolean deleteAllByCourseId(int id);
	public boolean deleteById(int id);
}
