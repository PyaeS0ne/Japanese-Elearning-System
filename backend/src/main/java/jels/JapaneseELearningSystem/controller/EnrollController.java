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
import jels.JapaneseELearningSystem.repository.courseRepo;
import jels.JapaneseELearningSystem.service.CourseService;
import jels.JapaneseELearningSystem.service.CourseVideoService;
import jels.JapaneseELearningSystem.service.LevelService;
import jels.JapaneseELearningSystem.service.StorageService;
import jels.JapaneseELearningSystem.service.UserService;
@RestController
public class EnrollController {
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepo userRepo;
	
	
	
	

	    @PostMapping("/enroll/userId/{user_id}/courseId/{course_id}")
	    public ResponseEntity<String> enrollUserInCourse(@PathVariable("user_id")  int userId,@PathVariable("course_id") int courseId) {
	        User user = userService.getById(userId);
	        Course course = courseService.getCourse(courseId);

	        if (user != null && course != null) {
	        	
	            user.getEnrolled_course().add(course);
	            System.out.println(user.getEnrolled_course());
	            
	            
	            userRepo.save(user);
	            course.setEnrolledUsers(course.getEnrolledUsers()+1);
	            
	            courseService.update(courseId, course);
	            
	            //course.getEnrolledUsers().add(user);
	            //courseService.create(course);

	            return ResponseEntity.ok("Enrollment successful");
	        } else {
	            return ResponseEntity.badRequest().body("User or Course not found");
	        }
	    }
	    
	   

	    @GetMapping("/enroll/users/{user_id}/courses")
	    public ResponseEntity<?> getCoursesByUserId(@PathVariable("user_id") int userId) {
	    		// Retrieve the user by user_id
	     
		      //Optional<User> optionalUser = userService.getUser(userId);
		      // if (optionalUser.isPresent()) {
		      // User user = optionalUser.get();
		    	
	    	 User user = userService.getById(userId);
	        // Access the enrolled_course property to get the courses
	        List<Course> enrolledCourses = user.getEnrolled_course();
	        if (enrolledCourses!= null) {
	        	System.out.println(enrolledCourses);
	        	 return ResponseEntity.ok().body(enrolledCourses);
			}
	         else {
	        	 return ResponseEntity.badRequest().body(" Course not found");
	         }
    
	    }
}
