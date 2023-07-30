package jels.JapaneseELearningSystem.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.entity.Test;

@Repository
@EnableJpaRepositories
public interface TestRepo extends JpaRepository<Test, Integer>{
	
	public List<Test> findAllByLevelId(int level_id);
	
	
	
	
}
