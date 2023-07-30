package jels.JapaneseELearningSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.repository.QuestionRepo;

@Service
public class QuestionServiceImpl implements QuestionService {
	

	@Autowired
	QuestionRepo questionRepo;
	
	@Override
	public Question create(Question question) {
		
		
		// TODO Auto-generated method stub
		return questionRepo.save(question);
	}

	@Override
	public List<Question> getAll() {
		// TODO Auto-generated method stub
		return questionRepo.findAll();
	}

	@Override
	public Question getById(int id) {
		// TODO Auto-generated method stub
		return questionRepo.findById(id).orElse(null);
	}

	@Override
	public Question update(int id, Question question) {
		// TODO Auto-generated method stub
		Question toUpdate= this.getById(id);
		if(toUpdate!=null) {
			
			toUpdate.setQuestion(question.getQuestion());
			toUpdate.setAnswerOptions(question.getAnswerOptions());
			toUpdate.setTest(question.getTest());
			return questionRepo.save(toUpdate);
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		
		// TODO Auto-generated method stub
		try {
			
			questionRepo.deleteById(id);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	
	}

	@Override
	public List<Question> getByTestId(int test_id) {
		// TODO Auto-generated method stub
		return questionRepo.findAllByTestId(test_id);
	}

}
