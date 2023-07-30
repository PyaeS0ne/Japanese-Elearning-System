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
import jels.JapaneseELearningSystem.service.CourseService;
import jels.JapaneseELearningSystem.service.CourseVideoService;
import jels.JapaneseELearningSystem.service.LevelService;
import jels.JapaneseELearningSystem.service.StorageService;
import jels.JapaneseELearningSystem.service.UserService;

//payment
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CourseController {
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LevelService levelService;
	
	@Autowired
	CourseVideoService courseVideoService;
	
	@Autowired
	StorageService storageService;
    

	//Level
	@GetMapping("/level")
	public List<Level> getLevel() {
		System.out.println("fetching levels");
		return levelService.getAllLevel();
	}
	
	@GetMapping("/course/level/{level_id}")
	public ResponseEntity<?> getAllByLevelId(
			@PathVariable("level_id") int levelID
	) {
		
		List<Course> courseList = courseService.getAllByLevelId(levelID);
		if (courseList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(courseList);
	}
	/*
	@PostMapping("/charge")
    public ResponseEntity<String> processPayment() {
        Stripe.apiKey = "YOUR_TEST_SECRET_KEY"; // Replace with your Stripe test secret key

        try {
            // Create a payment intent with a $0 amount to simulate a free transaction
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(0L)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // Send the client secret to the frontend
            return ResponseEntity.ok(intent.getClientSecret());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment processing failed.");
        }
    }
	*/
	//Course
	
	@GetMapping("/course/title/{title}")
	public ResponseEntity<?> findMovieByTitle(
			@PathVariable("title") String title
	) {
		List<Course> courseList = courseService.getByTitle(title);
		if (courseList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(courseList);
	}
	
	@GetMapping("/course")
	public ResponseEntity<?> getCourse() {
		List<Course> courseList= courseService.getAllCourse();
		
		
		return ResponseEntity.ok(courseList);
		
		
	}
	
	@GetMapping("/course/{course_id}")
	public ResponseEntity<Course> getCourse(
			@PathVariable("course_id") int courseID
	) {
		Course course = courseService.getCourse(courseID);
		if (course == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(course);
	}
	
	//Course Video
	
	@GetMapping("/course/{course_id}/course_videos")
	public ResponseEntity<?> getCourseVideosByCourseId(
			@PathVariable("course_id") int courseID
	) {
		List<CourseVideo> courseVideoList= courseVideoService.getAllByCourseId(courseID);
		if (courseVideoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(courseVideoList);
	}
	
	@GetMapping("/course_video")
	public List<CourseVideo> getCourseVideo() {
		return courseVideoService.getAllCourseVideo();
	}
	
	@GetMapping("/course_video/{course_video_id}")
	public ResponseEntity<CourseVideo> getCourseVideo(
			@PathVariable("course_video_id") int courseVideoID
	) {
		CourseVideo coursevideo = courseVideoService.getCourseVideo(courseVideoID);
		if (coursevideo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(coursevideo);
	}
	
	
	

}
