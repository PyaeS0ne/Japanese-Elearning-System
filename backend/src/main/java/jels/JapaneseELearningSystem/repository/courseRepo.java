package jels.JapaneseELearningSystem.repository;

import java.lang.System.Logger.Level;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.Knowledge;
@Repository
@EnableJpaRepositories
public interface courseRepo extends JpaRepository<Course, Integer>{
	public List<Course> findAllByLevelId(int level_id);
	
	
	@Query(value ="SELECT * FROM course m WHERE m.title LIKE CONCAT('%', :title ,'%')", nativeQuery = true)
	public List<Course> findByTitle(String title);
}
