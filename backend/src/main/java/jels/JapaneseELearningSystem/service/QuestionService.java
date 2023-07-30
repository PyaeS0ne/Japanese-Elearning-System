package jels.JapaneseELearningSystem.service;

import java.util.List;

import jels.JapaneseELearningSystem.entity.Question;

public interface QuestionService {
	
	public Question create(Question question );
	public List<Question> getAll();
	public Question getById(int id);
	public Question update(int id, Question question);
	public boolean delete(int id);
	public List<Question> getByTestId(int test_id);
	

}
