package jels.JapaneseELearningSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jels.JapaneseELearningSystem.entity.AnswerOption;
import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.entity.Test;
import jels.JapaneseELearningSystem.entity.TestResult;
import jels.JapaneseELearningSystem.service.QuestionService;
import jels.JapaneseELearningSystem.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	TestService testService;
	
	@Autowired
	QuestionService questionService;


	
	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		
		List<Test> testList=testService.getAll();
		
		return ResponseEntity.ok(testList);
	}
	@GetMapping("/level/{level_id}")
	public ResponseEntity<?> getAllByLevel(@PathVariable int level_id){
		
		List<Test> testList=testService.getAllByLevel(level_id);
		
		return ResponseEntity.ok(testList);
	}
	
	@GetMapping("/{test_id}")
	public ResponseEntity<?> getById(@PathVariable int test_id){
		Test test= testService.getById(test_id);
		
		if(test!=null) {
			return ResponseEntity.ok(test);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/question/{test_id}")
	public ResponseEntity<?> getAllQuestionByTest(@PathVariable int test_id){
		List<Question> questionList= questionService.getByTestId(test_id);
		System.out.println("que list by test id "+test_id+"" +questionList);
		return ResponseEntity.ok(questionList);
	}
	
	@PutMapping("/update/{test_id}")
	public ResponseEntity<?> update(@PathVariable int test_id, @RequestBody Test test){
		
		System.out.println("to update test  : "+test);
		Test updatedTest = testService.update(test_id, test);
		if(updatedTest!= null) {
			return ResponseEntity.ok(updatedTest);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/delete/{test_id}")
	public ResponseEntity<?> delete(@PathVariable int test_id){
		
		System.out.println("to delete test id  : "+test_id);
		
		List<Question>  todeleteQues= questionService.getByTestId(test_id);
		if(todeleteQues.size()>0) {
			System.out.println("test has question ...deleting questions");
			for (Question question : todeleteQues) {
				questionService.delete(question.getId());
			}
			System.out.println("all questions deleted ");
		}
		
		boolean deleted=testService.delete(test_id);
		if(deleted) {
			return ResponseEntity.ok(deleted);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/result")
	public ResponseEntity<?> getResult(@RequestBody List<AnswerOption> answers){
		System.out.println("nasers "+answers);
		
		TestResult result=testService.calculateScore(answers);
		System.out.println("Test result :"+result);
		
		return ResponseEntity.ok(result);
	}
	
//	@GetMapping("/question/{question_id}")
//	public ResponseEntity<?> getQuestionById(@PathVariable int question_id){
//		Question question= questionService.getById(question_id);
//		
//		if(question!= null) {
//			return ResponseEntity.ok(question);
//		}
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}
 
}
