package jels.JapaneseELearningSystem.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.CourseCreateRequest;
import jels.JapaneseELearningSystem.entity.CourseVideo;
import jels.JapaneseELearningSystem.entity.DocxContent;
import jels.JapaneseELearningSystem.entity.Knowledge;
import jels.JapaneseELearningSystem.entity.Level;
import jels.JapaneseELearningSystem.entity.Question;
import jels.JapaneseELearningSystem.entity.Test;
import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.entity.UserRole;
import jels.JapaneseELearningSystem.repository.UserRepo;
import jels.JapaneseELearningSystem.repository.courseVideoRepo;
import jels.JapaneseELearningSystem.service.CourseService;
import jels.JapaneseELearningSystem.service.CourseVideoService;
import jels.JapaneseELearningSystem.service.KnowledgeService;
import jels.JapaneseELearningSystem.service.LevelService;
import jels.JapaneseELearningSystem.service.QuestionService;
import jels.JapaneseELearningSystem.service.StorageService;
import jels.JapaneseELearningSystem.service.TestService;
import jels.JapaneseELearningSystem.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	StorageService storageService;

	@Autowired
	UserService userService;

	@Autowired
	CourseService courseService;
	
	@Autowired
	LevelService levelService;
	
	@Autowired
	CourseVideoService courseVideoService;
	
	@Autowired
	KnowledgeService knowledgeService;
	
	@Autowired
	TestService testService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	courseVideoRepo courseVideoRepo;
	
	@Autowired
	UserRepo userRepo;

	// ------
	// =============View UserLists=============================
	// View User List
	@GetMapping("/userList")
	public ResponseEntity<?> getAll() {
		List<User> users = userService.getAll();
		
		return ResponseEntity.ok(users);
		
	}

	// ==========View Admin List and Promote As An Admin=========
	@GetMapping("/adminList")
	public ResponseEntity<?> getByRole_admin() {
		List<User> admins = userService.getByRole(UserRole.admin);
		System.out.println("these are admins :"+admins);
		
		return ResponseEntity.ok(admins);
		
	}
	
	@GetMapping("/nonAdminList")
	public ResponseEntity<?> getByRole_user() {
		List<User> users = userService.getByRole(UserRole.user);
		System.out.println("these are non admin :"+users);
		
		return ResponseEntity.ok(users);
		
	}

	@PostMapping("/promote/{user_id}")
	public ResponseEntity<User> promoteAdmin(@PathVariable int user_id) {
		User promotedAdmin = userService.promoteAdmin(user_id);
		if (promotedAdmin == null) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(promotedAdmin, HttpStatus.OK);
		}
	}
	
	@PostMapping("/demote/{user_id}")
	public ResponseEntity<User> demoteUser(@PathVariable int user_id) {
		User promotedAdmin = userService.demoteUser(user_id);
		if (promotedAdmin == null) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(promotedAdmin, HttpStatus.OK);
		}
	}

	//////////////////////////////////////////////////////////////////////

	// ============Course Management=======================
	@PostMapping("/course/create")
	public ResponseEntity<?> createCourse(@RequestBody Course course){
		System.out.println("creating course");
		
	
		if(course.getImagePath()==null) {
			return  ResponseEntity.badRequest().body("image path is empty");
		}
		if(course.getLevel()==null) {
			return ResponseEntity.badRequest().body("level is not define");
		}
		
		
		Course createdCourse= courseService.create(course);
		if(createdCourse==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		return ResponseEntity.ok(createdCourse);
		
	}
	
	@PostMapping("/createCourseVideo")
	public ResponseEntity<?> createCourseVideo(@RequestBody CourseVideo courseVideo){
		System.out.println("creating cousre videos "+courseVideo);
		
		try {
			CourseVideo video= courseVideoService.create(courseVideo);
			System.out.println("created course video : "+video);
			return ResponseEntity.ok(video);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error creating vidoe "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	 
	
		
		
		
	
		
		
	}
	
//	@DeleteMapping("/courseVideo/delete/{course_id}")
//	public ResponseEntity<?> deleteCourseVideo(@PathVariable int course_id){
//		
//		List<CourseVideo> videos= courseVideoService.getAllByCourseId(course_id);
//		for (CourseVideo courseVideo: videos) {
//			storageService.delete(courseVideo.getPath());
//		}
//		
//		//delete Course videos with course_id
//		boolean deleted =courseVideoService.deleteAllByCourseId(course_id);
//		if(deleted) {
//			return ResponseEntity.ok(deleted);
//		}
//		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	}
//	
	@DeleteMapping("/courseVideo/deleteByVideoId/{video_id}")
	@Transactional
	public ResponseEntity<?> deleteCourseVideo(@PathVariable int video_id){
		
		CourseVideo toDelete=courseVideoService.getCourseVideo(video_id);
		String videoPath=toDelete.getPath();
		
		//Delete watched video
		List<Long> watchedVideoIds =courseVideoRepo.findAllVideoIds();
		for (Long watchedVideoId: watchedVideoIds) {
			System.out.println("watched video Id "+watchedVideoId);
			System.out.println("video id "+ toDelete.getId());
		if(toDelete.getId() == watchedVideoId ) {
			 userRepo.deleteWatchedVideosByVideoId(toDelete.getId());
			 System.out.println("Watched video with videoId " +watchedVideoId  + " deleted successfully.");
		}
		}
		boolean deleted = courseVideoService.deleteById(video_id);
		
		if(deleted) {
			storageService.delete(videoPath);
			return ResponseEntity.ok(deleted);
			
		}
		return new ResponseEntity<>(deleted, HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping("/courseVideo/update/{video_id}")
	public ResponseEntity<?> updateCourseVideo(@PathVariable int video_id,@RequestBody CourseVideo courseVideo){
		
		CourseVideo updatedVideo=courseVideoService.update(video_id, courseVideo);
		
		if(updatedVideo!=null) {
			return ResponseEntity.ok(updatedVideo);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	
//	@PostMapping("/course/create")
//	public ResponseEntity<Course> create(@Valid @RequestPart("jsonCourse") Course course,@RequestPart("jsonLevel") Level level,
//			@RequestPart("imageFile") MultipartFile imageFile,
//			 @RequestPart(value =  "videoFile" , required = false) MultipartFile[] videofiles) {
//        
//		//Save imageFile
//		String imagePath= storageService.saveFile(imageFile);
//		course.setImagePath(imagePath);
//		course.setLevel(level);
//		//Course Created 
//		Course courses = courseService.create(course);
//		int i=1;
//		
//		//Save VideoFiles
//		if (videofiles != null) {
//			for (MultipartFile file : videofiles) {
//				CourseVideo tostore = new CourseVideo ();
//				tostore.setTitle("Day("+i+")");
//				tostore.setPath(storageService.saveFile(file));
//				tostore.setCourse(course);//
//				courseVideoService.create(tostore);
//				i++;
//			}
//		}
//		System.out.println("without image");
//		return ResponseEntity.ok(courses);
//	}
	
	@PutMapping("/course/update/{id}")
	public ResponseEntity<Course> update( @PathVariable int id, @RequestBody Course course) {

		Course updatedCourse = courseService.update(id, course);
		if (updatedCourse == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
		}
		return ResponseEntity.ok(updatedCourse);

	}
	
	@DeleteMapping("/course/delete/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable int id) {
		
		Course course=courseService.getCourse(id);
		
		
		
		if(course==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//delete imagePath
		storageService.delete(course.getImagePath());
		
		//delete video path
		List<CourseVideo> videos= courseVideoService.getAllByCourseId(id);
		for (CourseVideo courseVideo: videos) {
			storageService.delete(courseVideo.getPath());
		}
		
		//delete Course videos with course_id
		courseVideoService.deleteAllByCourseId(id);
		
		//delete Course with id also include image Column
		boolean deleted = courseService.delete(id);
		
		
		if (!deleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();
	}

	///////////////////////////////////////////////////////////////



	// ==========Knowledge Management======================
	
	@DeleteMapping("/knowledge/delete/{knowledge_id}")
	public ResponseEntity<?> deleteKnowledge(@PathVariable int knowledge_id) {
		
		System.out.println("knowledge id :"+knowledge_id);
		Knowledge knowledge=knowledgeService.getById(knowledge_id);
		System.out.println(knowledge);
		
		if(knowledge==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		storageService.delete(knowledge.getContentPath());

		boolean deleted = knowledgeService.delete(knowledge_id);
		
		
		if (!deleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok(deleted);
	}
	
	@PutMapping("/knowledge/update/{id}")
	public ResponseEntity<Knowledge> update( @PathVariable int id, @RequestBody Knowledge knowledge) {

		Knowledge updatedKnowledge = knowledgeService.update(id, knowledge);
		if (updatedKnowledge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
		}
		return ResponseEntity.ok(updatedKnowledge);

	}
	@PostMapping("/knowledge/create")
	public ResponseEntity<Knowledge> create(@Valid @RequestBody Knowledge knowledge
			//, @RequestPart ("contentFile") MultipartFile contentFile
			) {

		//String contentPath= storageService.saveFile(contentFile);
		System.out.println("here"+knowledge);
		
		//knowledge.setContentPath(contentPath);
		//System.out.println(knowledge.getContentPath());
		Knowledge kn = knowledgeService.create(knowledge);

		return ResponseEntity.ok(kn);
	}
	
	//=============Test Management ================
	
	@PostMapping("/test/create")
	public ResponseEntity<?> createTest(@RequestBody Test test){
		
		Test createdTest=  testService.create(test);
		
		if(createdTest!= null) {
			System.out.println("created test "+createdTest);
			return ResponseEntity.ok(createdTest);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/admin/question/create")
	public ResponseEntity<?> createQuestion(@RequestBody Question question){
		
		Question createdQuestion= questionService.create(question);
		
		
		if(createdQuestion!=null) {
			return ResponseEntity.ok(createdQuestion);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	// ============Storage Management=============
	@PostMapping("/file/create")
	public ResponseEntity<String> crateFile(@RequestParam("file") MultipartFile file) {
		System.out.println("in file create");
		String path= storageService.saveFile(file);
		System.out.println(path);
		return ResponseEntity.ok(path);
	}

//	@PostMapping("/file/createMultiple")
//	public ResponseEntity<List<String> >crateMultipleFile(@RequestParam ("file") MultipartFile []files) {
//	return ResponseEntity.ok( storageService.saveMultipleFile(files));
//	}
	@GetMapping("file/load")
	public ResponseEntity<?> loadfile(@RequestParam("fileName") String fileName) {
		return ResponseEntity.ok(storageService.readDocxFile(fileName));

	}
	
	@PutMapping("file/update")
	public ResponseEntity<?> updateFile(@RequestParam("file") MultipartFile file
			,@RequestParam("oldFileName") String oldFileName){
		
		System.out.println("filename : "+oldFileName +"file : "+file);
		String newFilePath=storageService.update(oldFileName, file);
		
		if(newFilePath==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(newFilePath);
	}

	@GetMapping("/readDocx")
	public DocxContent getDocxContent(@RequestParam("fileName") String filename) {

		System.out.println("at line 48");
		return storageService.readDocxFile(filename);
	}

	@DeleteMapping("/file/delete/{fileName}")
	public ResponseEntity<?> delete(@PathVariable String fileName) {

		System.out.println("here");
		boolean deleted = storageService.delete(fileName);
		if (!deleted) {
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}
		return ResponseEntity.ok().build();

	}

	

}
