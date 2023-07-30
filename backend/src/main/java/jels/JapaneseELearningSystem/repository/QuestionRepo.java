package jels.JapaneseELearningSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.entity.User;

@Repository
@EnableJpaRepositories
public interface QuestionRepo extends JpaRepository<Question, Integer>{
	
	@Query(value = "select * from question_table where test_id=:test_id", nativeQuery = true)
	public List<Question> findAllByTestId(int test_id);
	
	

}
