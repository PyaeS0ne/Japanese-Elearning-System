package jels.JapaneseELearningSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jels.JapaneseELearningSystem.entity.Knowledge;
@Repository
@EnableJpaRepositories
public interface KnowledgeRepo  extends JpaRepository<Knowledge, Integer>{
	
	 @Query(value ="SELECT * FROM knowledge m WHERE m.tag LIKE CONCAT('%', :tag ,'%')", nativeQuery = true)
	 List<Knowledge> findBytag(@RequestParam("tag")String tag);
	

}
