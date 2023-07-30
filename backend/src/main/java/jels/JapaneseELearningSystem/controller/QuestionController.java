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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
	
	
	@Autowired
	QuestionService questionService;

	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		List<Question> questionList= questionService.getAll();
		return ResponseEntity.ok(questionList);
	}
	
	@GetMapping("/{question_id}")
	public ResponseEntity<?> getById(@PathVariable int question_id){
		Question question= questionService.getById(question_id);
		
		if(question!= null) {
			return ResponseEntity.ok(question);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	
	}
	
	
	

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Question question){
		
		System.out.println(" que : "+question);
		Question createdQue = questionService.create(question);
		
		if(createdQue!=null) {
			System.out.println(" created que : "+ createdQue);
			return ResponseEntity.ok(createdQue);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/update/{question_id}")
	public ResponseEntity<?> update(@PathVariable int question_id, @RequestBody Question question){
		 System.out.println("to update que :"+question);
		 
		 Question updatedQue = questionService.update(question_id, question);
		 
		 if(updatedQue!=null) {
			 return ResponseEntity.ok(updatedQue);
		 }
		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/delete/{question_id}")
	public ResponseEntity<?> delete(@PathVariable int question_id){
		 
		 boolean deleted= questionService.delete(question_id);
		 
		 if(deleted) {
			 System.out.println("successfully delelted qusetion");
			 return ResponseEntity.ok(deleted);
			 
		 }
		 System.out.println("error while deleting question");
		 return new ResponseEntity<>(deleted, HttpStatus.BAD_REQUEST);
	}

	
}
