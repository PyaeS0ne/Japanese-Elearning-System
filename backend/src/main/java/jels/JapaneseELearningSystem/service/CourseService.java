package jels.JapaneseELearningSystem.service;

import java.util.List;

import jels.JapaneseELearningSystem.entity.Course;

public interface CourseService {
	
public List<Course> getAllByLevelId(int level_id);
	
	public List<Course> getAllCourse();
	
	public Course getCourse(int id);
	
	public Course create(Course course);

	public Course update(int id, Course course);

	public boolean delete(int id);
	
	public List<Course> getByTitle(String title);
	
}
