package jels.JapaneseELearningSystem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jels.JapaneseELearningSystem.entity.Knowledge;
import jels.JapaneseELearningSystem.repository.KnowledgeRepo;
@Service
public class KnowledgeServiceImpl implements KnowledgeService{

	@Autowired
	private KnowledgeRepo knowledgeReop;
	
	@Override
	public  List<Knowledge> getAll() {
		// TODO Auto-generated method stub
		return knowledgeReop.findAll();
	}
	@Override
	public Knowledge getById(int knowledge_id) {
		// TODO Auto-generated method stub
		return knowledgeReop.findById(knowledge_id).orElse(null);
		
	}
	
	@Override
	public Knowledge create(Knowledge knowledge) {
		// TODO Auto-generated method stub
		knowledge.setDate(LocalDate.now());
		return knowledgeReop.save(knowledge);
		
		
	}

	@Override
	public Knowledge update(int id,Knowledge knowledge) {
		// TODO Auto-generated method stub	
		Knowledge toUpdate= this.getById(id);
		
		//System.out.println(toUpdate);
		
		if(toUpdate==null) {
			return null;
		}
		toUpdate.setTitle(knowledge.getTitle());
		toUpdate.setTag(knowledge.getTag());
		toUpdate.setFeatureImage(knowledge.getFeatureImage());
		toUpdate.setIntro(knowledge.getIntro());
		toUpdate.setContentPath(knowledge.getContentPath());
		knowledgeReop.save(toUpdate);
		return toUpdate;
	}

	@Override
	public boolean delete(int knowledge_id) {
		// TODO Auto-generated method stub	
		Knowledge toDeleteKnowledge = this.getById(knowledge_id);
		if (toDeleteKnowledge==null) {
			return false;
		}
		
		knowledgeReop.deleteById(knowledge_id);
		return true;
	}
	@Override
	public List<Knowledge> getBytag(String tag) {
		// TODO Auto-generated method stub
		return knowledgeReop.findBytag(tag);
	}

	



	


}
