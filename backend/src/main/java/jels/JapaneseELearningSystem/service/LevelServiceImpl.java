package jels.JapaneseELearningSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jels.JapaneseELearningSystem.entity.Level;
import jels.JapaneseELearningSystem.repository.levelRepo;

@Service
public class LevelServiceImpl implements LevelService {
   
	@Autowired
	levelRepo levelRepo;
	
	@Override
	public List<Level> getAllLevel() {
		return levelRepo.findAll();
	}

	@Override
	public Level getById(int id) {
		return levelRepo.findById(id).orElse(null);
	}

	@Override
	public Level create(Level level) {
		return levelRepo.save(level);

	}
	

}
