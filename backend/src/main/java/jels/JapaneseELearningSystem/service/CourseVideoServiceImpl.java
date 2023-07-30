package jels.JapaneseELearningSystem.service;

import java.time.LocalDateTime;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.CourseVideo;
import jels.JapaneseELearningSystem.entity.Level;
import jels.JapaneseELearningSystem.repository.courseVideoRepo;

@Service
public class CourseVideoServiceImpl implements CourseVideoService{
	@Autowired
	courseVideoRepo courseVideoRepo;

	@Override
	public List<CourseVideo> getAllCourseVideo() {
		return courseVideoRepo.findAll();
	}

	@Override
	public CourseVideo getCourseVideo(int id) {
		return courseVideoRepo.findById(id).orElse(null);
	}

	@Override
	public CourseVideo create(CourseVideo courseVideo) {
		
		return courseVideoRepo.save(courseVideo);
	}
	
	@Override
	public List<CourseVideo> getAllByCourseId(int course_id) {
		
		return courseVideoRepo.findAllByCourseId(course_id);
	}
	
	@Override
	public boolean deleteAllByCourseId(int id) {
		List<CourseVideo> toDelete= this.getAllByCourseId(id);		
		if (toDelete.size()<1) {
			return false;
		}
		
		for (CourseVideo courseVideo : toDelete) {
			courseVideoRepo.deleteById(courseVideo.getId());
			
		}
		
		return true;
	}

	@Override
	public boolean deleteById(int id) {
		CourseVideo toDelete=this.getCourseVideo(id);
		
		
		try {
			courseVideoRepo.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println("error while deleting video");
			return false;
		}
		
		
	}

	@Override
	public CourseVideo update(int video_id,CourseVideo courseVideo) {
		CourseVideo toUpdate=this.getCourseVideo(video_id);
		
		if(toUpdate!=null) {
			System.out.println("not null video creating");
			toUpdate.setTitle(courseVideo.getTitle());
			toUpdate.setPath(courseVideo.getPath());
			toUpdate.setCourse(courseVideo.getCourse());
			
			courseVideoRepo.save(toUpdate);
			return toUpdate;
			
		}
		System.out.println(" it all null");
		return null;
	}


}
