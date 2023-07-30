package jels.JapaneseELearningSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jels.JapaneseELearningSystem.entity.AnswerOption;
import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.entity.Test;
import jels.JapaneseELearningSystem.entity.TestResult;
import jels.JapaneseELearningSystem.repository.TestRepo;


@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestRepo testRepo;
	

	@Override
	public List<Test> getAll() {
		// TODO Auto-generated method stub
		return testRepo.findAll();
	}

	@Override
	public Test getById(int test_id) {
		// TODO Auto-generated method stub
		return testRepo.findById(test_id).orElse(null);
	}

	@Override
	public Test create(Test test) {
		// TODO Auto-generated method stub
		return testRepo.save(test);
	}

	@Override
	public Test update(int test_id, Test test) {
		// TODO Auto-generated method stub
		Test toUpdate=this.getById(test_id);
		
		if(toUpdate!=null) {
			toUpdate.setTitle(test.getTitle());
			toUpdate.setLevel(test.getLevel());
			
			return testRepo.save(toUpdate);
		}
		
		return null;
	}

	@Override
	public boolean delete(int test_id) {
		// TODO Auto-generated method stub
		
		try {
			 testRepo.deleteById(test_id);
			 System.out.println("test deleted ");
			 return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error deleting test");
			return false;
		}
		
	}



	@Override
	public List<Question> getQuestion(int test_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestResult calculateScore(List<AnswerOption> Answers) {
		// TODO Auto-generated method stub
		int correctAns=0;
		int inCorrectAns=0;
		//int totalAns=Answers.size();
		
		TestResult result= new TestResult();
		
		
		for (AnswerOption ans : Answers) {
			
		if(ans.getIsCorrect()) {
			correctAns+=1;
			
		}else {
			inCorrectAns+=1;
			
		}
			
		}
		result.setCorrectAns(correctAns);
		result.setInCorrectAns(inCorrectAns);
		result.setTotalAns(correctAns+inCorrectAns);
		
		return result;
	}

	@Override
	public List<Test> getAllByLevel(int level_id) {
		// TODO Auto-generated method stub
		return testRepo.findAllByLevelId(level_id);
	}


}
