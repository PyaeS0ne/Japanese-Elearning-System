package jels.JapaneseELearningSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jels.JapaneseELearningSystem.entity.AnswerOption;
import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.entity.Test;
import jels.JapaneseELearningSystem.entity.TestResult;
@Service
public interface TestService {
	
	public Test create(Test test);
	public Test update(int test_id,Test test);
	public boolean delete(int test_id);
	public List<Test> getAll();
	public Test getById(int test_id);
	public TestResult calculateScore(List<AnswerOption> Answers);
	public List<Question> getQuestion(int test_id);
	public List<Test> getAllByLevel(int level_id);
	

}
