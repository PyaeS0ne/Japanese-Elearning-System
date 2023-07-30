package jels.JapaneseELearningSystem.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jels.JapaneseELearningSystem.entity.Knowledge;

import jels.JapaneseELearningSystem.service.KnowledgeService;
import jels.JapaneseELearningSystem.service.StorageService;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

	@Autowired
	KnowledgeService knowledgeService;
	@Autowired
	StorageService storageService;
	

	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		List<Knowledge> knowledges= knowledgeService.getAll();
		//System.out.println(knowledges);
		
		return ResponseEntity.ok(knowledges);
		}
	@GetMapping("/{id}")
	public ResponseEntity<Knowledge> getById(@PathVariable int id){
		return ResponseEntity.ok(knowledgeService.getById(id)) ;
	}
//	@GetMapping("/getOneKnowledge/{knowledge}")
//	public ResponseEntity<Knowledge> getKnowledge(@PathVariable Knowledge knowledge){
//		return ResponseEntity.ok(knowledgeService.getById(knowledge.getId())) ;
//	}
	
	@GetMapping("/tag")
	public ResponseEntity<?> getBytag(@RequestParam("tag") String tag){
		 List<Knowledge> knowledges= knowledgeService.getBytag(tag);
		 System.out.println(knowledges);
		 
		 return ResponseEntity.ok(knowledges);
	}

	



	
	




}
