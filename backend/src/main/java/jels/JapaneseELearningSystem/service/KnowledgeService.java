package jels.JapaneseELearningSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jels.JapaneseELearningSystem.entity.Knowledge;
import jels.JapaneseELearningSystem.repository.KnowledgeRepo;


public interface KnowledgeService  {
	
	public List<Knowledge> getAll();
	public Knowledge getById(int knowledge_id);
	public List<Knowledge> getBytag(String tag);
	public Knowledge create(Knowledge knowledge);
	public Knowledge update(int id,Knowledge knowledge);
	public boolean delete(int knowledge_id);

	

}
