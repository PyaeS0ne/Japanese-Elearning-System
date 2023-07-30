package jels.JapaneseELearningSystem.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.CourseVideo;
import jels.JapaneseELearningSystem.entity.Level;

@Repository
@EnableJpaRepositories
public interface courseVideoRepo extends JpaRepository<CourseVideo, Integer>{
	
	
	//@Query("SELECT * FROM CourseVideo WHERE course_id =:course_id")
	public List<CourseVideo> findAllByCourseId(int course_id);
	
	 @Query(value="select wv.video_id from watched_video wv", nativeQuery = true)
	 public List<Long> findAllVideoIds();
	 
}






