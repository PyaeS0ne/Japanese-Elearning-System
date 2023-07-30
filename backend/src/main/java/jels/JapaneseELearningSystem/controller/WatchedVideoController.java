package jels.JapaneseELearningSystem.controller;

import java.util.List;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.CourseVideo;
import jels.JapaneseELearningSystem.entity.Level;
import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.repository.UserRepo;
import jels.JapaneseELearningSystem.service.CourseService;
import jels.JapaneseELearningSystem.service.CourseVideoService;
import jels.JapaneseELearningSystem.service.LevelService;
import jels.JapaneseELearningSystem.service.StorageService;
import jels.JapaneseELearningSystem.service.UserService;
@RestController
public class WatchedVideoController {
	@Autowired
	CourseVideoService coursevideoService;
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepo userRepo;
	
	

	    @PostMapping("/watched/userId/{user_id}/videoId/{video_id}")
	    public ResponseEntity<String> enrollUserInCourse(@PathVariable("user_id")  int userId,@PathVariable("video_id") int videoId) {
	        User user = userService.getById(userId);
	        CourseVideo coursevideo = coursevideoService.getCourseVideo(videoId);

	        if (user != null && coursevideo != null) {
	            user.getWatched_video().add(coursevideo);
	            System.out.println(user.getWatched_video());
	           // course.getUserList().add(user);
	            
	            userRepo.save(user);
	            //course.getEnrolledUsers().add(user);
	            //courseService.create(course);

	            return ResponseEntity.ok("Successful");
	        } else {
	            return ResponseEntity.badRequest().body("User or CourseVideo not found");
	        }
	    }
	    

	    @GetMapping("/watched/users/{user_id}/videos")
	    public ResponseEntity<?> getCoursesByUserId(@PathVariable("user_id") int userId) {
	    		// Retrieve the user by user_id
	     
		      //Optional<User> optionalUser = userService.getUser(userId);
		      // if (optionalUser.isPresent()) {
		      // User user = optionalUser.get();
		    	
	    	 User user = userService.getById(userId);
	        // Access the enrolled_course property to get the courses
	        List<CourseVideo> watchedVideos = user.getWatched_video();
	        if (watchedVideos!= null) {
	        	System.out.println(watchedVideos);
	        	 return ResponseEntity.ok().body(watchedVideos);
			}
	         else {
	        	 return ResponseEntity.badRequest().body(" watchedVideos not found");
	         }
    
	    }
}
