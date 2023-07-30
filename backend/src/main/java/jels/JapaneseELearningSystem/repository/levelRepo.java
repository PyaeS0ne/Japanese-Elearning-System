package jels.JapaneseELearningSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import jels.JapaneseELearningSystem.entity.Level;

@Repository
public interface levelRepo extends JpaRepository<Level, Integer> {

}
