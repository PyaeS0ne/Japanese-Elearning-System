package jels.JapaneseELearningSystem.service;

import java.time.LocalDateTime;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.CourseVideo;
import jels.JapaneseELearningSystem.entity.Level;
import jels.JapaneseELearningSystem.repository.courseRepo;

@Service
public class CourseServiceImple implements CourseService{
	@Autowired
	courseRepo courseRepo;
	
	@Override
	public List<Course> getAllByLevelId(int level_id) {
		return courseRepo.findAllByLevelId(level_id);
	}

	@Override
	public List<Course> getAllCourse() {
		return courseRepo.findAll();
		
	};
   
	@Override
	public Course getCourse(int id) {
		return courseRepo.findById(id).orElse(null);
	}
	
	@Override
	public Course create(Course course) {
		return courseRepo.save(course);
	}
    
	@Override
	public boolean delete(int id) {
		Course course = this.getCourse(id);
		if (course == null) {
			return false;
		}
		courseRepo.deleteById(id);
		return true;
	}

	@Override
	public Course update(int id, Course course) {
		Course toUpdateCourse = this.getCourse(id);
		if (toUpdateCourse == null) {
			return null;
		}
		toUpdateCourse.setTitle(course.getTitle());
		toUpdateCourse.setDescription(course.getDescription());
		toUpdateCourse.setRefBook(course.getRefBook());
		toUpdateCourse.setImagePath(course.getImagePath());
		toUpdateCourse.setPdfPath(course.getPdfPath());
		toUpdateCourse.setLevel(course.getLevel());
		toUpdateCourse.setFee(course.getFee());
		//toUpdateCourse.setCourseVideo(course.getCourseVideo());
		courseRepo.save(toUpdateCourse);
		return toUpdateCourse;
	}
	@Override
	public List<Course> getByTitle(String title) {
		return courseRepo.findByTitle(title);
	}


	
}
