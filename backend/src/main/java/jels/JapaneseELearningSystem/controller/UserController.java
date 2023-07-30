package jels.JapaneseELearningSystem.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jels.JapaneseELearningSystem.entity.ChangeName;
import jels.JapaneseELearningSystem.entity.ChangePassword;
import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.LoginRequest;
import jels.JapaneseELearningSystem.entity.RegisterRequest;
import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.service.CourseService;
import jels.JapaneseELearningSystem.service.EmailSenderService;
import jels.JapaneseELearningSystem.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CourseService courseService;
//	@Autowired
//	PasswordEncoder pwdEncoder;
	
	@Autowired
	private EmailSenderService senderService;
	
	@GetMapping("/userCourseList/{userId}")
	public ResponseEntity<?> getCourse(@PathVariable int userId){
		System.out.println("user Id :" +userId);
		List<Course> courseList= userService.getcourList(userId);
		System.out.println(courseList);
		if(courseList.size()<1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(courseList);
	}
	
//	@PostMapping("/addCourse")
//	public ResponseEntity<?> addCourseToUser(@RequestParam("CouserId") int courseId,@RequestParam("UserId") int userId ){
//		
//		System.out.println("courseId:"+courseId+" userID:"+userId);
//		User user1=userService.getById(userId);
//		
//		try {
//			Course course= courseService.getById(courseId);
//			User user = userService.getById(userId);
//			if(course==null||user==null) {
//				System.out.println("User or Course not found");
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			}else if(user.getEnrolled_course().contains(course)) {
//				System.out.println("this course is already enrolled");
//				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//			}else {
//				
//			
//			System.out.println(course);
//			System.out.println(user);
//			user.getEnrolled_course().add(course);
//			}
//			
//			
//			userService.update(user);
//			//courseService.update(course);
//			return ResponseEntity.ok(true);
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		
//		
//		
//	}
	//-----------------------------------------------
	
	//=============Login=================================
		@PostMapping("/login")
		public ResponseEntity<User> login(@Valid @RequestBody LoginRequest loginRequest){	
			//.
			User user=userService.checkLoginUser(loginRequest.getEmail(),loginRequest.getPassword());
			System.out.println(loginRequest);
			if (user == null) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok().body(user);
		}
		

		//============Register===============================
		@PostMapping("/register")
		public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest user){
			System.out.println(user);
			String serverGeneratedPassword=senderService.generatePassword();
			
			User createdUser=userService.register(user,serverGeneratedPassword);
			if(createdUser==null) {
				return ResponseEntity.badRequest()
						.body("User with same gmail already exists!");
			}
			else {
				createdUser.setPassword(serverGeneratedPassword);
				senderService.sendEmail(user.getEmail(), "Account Verification", "To continue account creating, please enter with this password : "+ serverGeneratedPassword);
				return ResponseEntity.ok().body(createdUser);
			}
		}
		
		//===============Forget Password=====================
		@PostMapping("/forgetPassword")
		public ResponseEntity<?> forget(@Valid @RequestParam("email") String email){
			User checkUser=userService.checkRegisterUser(email);
			if(checkUser==null) {
				return ResponseEntity.badRequest().body("The email you entered doesn't exist..");
			}
			else {
				String serverGeneratedPassword=senderService.generatePassword();
				senderService.sendEmail(checkUser.getEmail(), "Password Reset", "Login using this password : "+serverGeneratedPassword);
				userService.savePwd(checkUser.getId(), serverGeneratedPassword);
				return ResponseEntity.ok().body(checkUser);
			}
		}
		
		//=====================Get User By Id=============================
		@GetMapping(value="/user/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<User> getById(@PathVariable int id){
			User user=userService.getById(id);
			if(user==null) {
				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<User>(user,HttpStatus.OK);
			}
		}
		
		//=====================User Create(for testing)====================
		@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<User> createUser(@RequestBody User user){
			User new_user=userService.create(user);
			if(new_user==null) {
				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<User>(user,HttpStatus.OK);
			}
		}
		
		//========================Profile============================
		@GetMapping("/profile/{id}")
		public ResponseEntity<User> getProfile(@PathVariable int id){
			User user=userService.getById(id);
			if(user==null) {
				return ResponseEntity.notFound().build();
			}
			else {
				return ResponseEntity.ok().body(user);
			}
		}
			
		//==============Change Password=============================
		@PostMapping("/changePwd")
		public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword chPwd)throws IOException{
			User user=userService.getById(chPwd.getOri_id());
			if(user==null) {
				return new ResponseEntity<Object>(new Exception("Something wrong"),HttpStatus.CONFLICT);
			}
			if(!chPwd.getComfirm_new_pwd().equals(chPwd.getNew_pwd())) {
				return new ResponseEntity<Object>(new Exception("Comfirm password does not match"),HttpStatus.CONFLICT);
			}
//			if(!pwdEncoder.matches(chPwd.getCurrent_pwd(), user.getPassword())) {
//				return new ResponseEntity<Object>(new Exception("Incorrect password"),HttpStatus.CONFLICT);
//			}
			if(!chPwd.getCurrent_pwd().equals(user.getPassword())) {
				return new ResponseEntity<Object>(new Exception("Incorrect password"),HttpStatus.CONFLICT);
			}
			else {
				userService.updatePwd(user.getId(), chPwd.getNew_pwd());
				return ResponseEntity.ok().body(user);
			}
		}
		
		//=========================Change Name==================================
		@PostMapping("/changeName")
		public ResponseEntity<Object> changeName(@Valid @RequestBody ChangeName chName){
			User user=userService.updateName(chName.getOri_id(), chName.getNew_name());
			if(user==null) {
				return new ResponseEntity<Object>(new Exception("Something wrong"),HttpStatus.CONFLICT);
			}
			userService.updateName(chName.getOri_id(), chName.getNew_name());
			return ResponseEntity.ok().body(user);
		}
		
		//.................User Can Do...........................
		//SearchByTitle
//			@GetMapping("/course/title/{title}")
//			public ResponseEntity<Boolean> findByCourseTitle(@PathVariable("title") String title){
//				Course course=courseService.getByTitle(title);
//				if(course==null) {
//					return ResponseEntity.ok().body(false);
//				}
//				return ResponseEntity.ok().body(true);
//			}
//	

}
