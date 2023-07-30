package jels.JapaneseELearningSystem.service;

import java.util.List;

import jels.JapaneseELearningSystem.entity.Level;

public interface LevelService {

	public List<Level> getAllLevel();

	public Level getById(int id);
	public Level create(Level level);


}
