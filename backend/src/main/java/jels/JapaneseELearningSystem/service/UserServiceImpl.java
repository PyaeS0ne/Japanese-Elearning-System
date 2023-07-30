package jels.JapaneseELearningSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.RegisterRequest;
import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.entity.UserRole;
import jels.JapaneseELearningSystem.repository.UserRepo;
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepo userRepo;
	
//	@Autowired
//	PasswordEncoder pwEncoder;
	
	@Override
	public List<User> getAll() {
		// Get All Users
		return userRepo.findAll();
	}

	
	@Override
	public User getById(int id) {
		return userRepo.findById(id).orElse(null);
	}
	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}
	@Override
	public boolean addCourse(Course course, User user) {
		
		user.getEnrolled_course().add(course);
		 return true;
	}
	
	@Override
	public List<Course> getcourList(int userId) {
		// TODO Auto-generated method stub
		User user= this.getById(userId);
		System.out.println(user);
		return user.getEnrolled_course();
		
	}
	
	@Override
	public User create(User user) {
		// Check IF user with same gmail exists
		User tempGmailCheckUser = userRepo.findByEmail(user.getEmail());
		if (tempGmailCheckUser != null) {
			return null;
		}
		// Create User
		user.setRole(UserRole.user);
		user.setName(user.getName());
		user.setPassword(user.getPassword());
		user.setEmail(user.getEmail());
		return userRepo.save(user);
	}
	
	@Override
	public User register(RegisterRequest registerUser, String password) {
		// create and check if user with the same gmail exists
		User EmailCheckUser= userRepo.findByEmail(registerUser.getEmail());
		if(EmailCheckUser!=null) {
			System.out.println("email is alreay in use");
			return null;
		}
		else {
			User createdUser=new User();
			createdUser.setRole(UserRole.user);
			createdUser.setName(registerUser.getName());
			createdUser.setEmail(registerUser.getEmail());
			createdUser.setPassword(password);
			//user.setPassword(pwEncoder.encode(user.getPassword()));
			return userRepo.save(createdUser);
		}
	}
	

	@Override
	public User updateName(int id, String name) {
		// Change Name
		User toUpdateUserName=this.getById(id);
		if(toUpdateUserName!=null) {
			toUpdateUserName.setName(name);
		}
		return userRepo.save(toUpdateUserName);
	}

	@Override
	public boolean delete(int id) {
		// Delete User
		User user=this.getById(id);
		if(user==null) {
			return false;
		}
		userRepo.deleteById(id);
		return true;
	}
	
	@Override
	public User updatePwd(int id, String newPwd) {
		// TODO Auto-generated method stub
		User toUpdateUserPwd=getById(id);
		if(toUpdateUserPwd!=null) {
			toUpdateUserPwd.setPassword(newPwd);
		}
		return userRepo.save(toUpdateUserPwd);
	}

//	@Override
//	public void updatePwd(int id, String newPwd) {
//		// Change Password
//		User toUpdateUserPwd=getById(id);
//		if(toUpdateUserPwd!=null) {
//			toUpdateUserPwd.setPassword(pwEncoder.encode(newPwd));
//			userRepo.save(toUpdateUserPwd);
//			System.out.println("pwd updated");
//		}
//		
//	}

	@Override
	public User checkLoginUser(String email, String password) {
		// TODO Auto-generated method stub
		User user=userRepo.findByEmail(email);
		if(user==null) {
			return null;
		}
		if(!user.getPassword().equals(password)) {
			return null;
		}
		return userRepo.save(user);
	}
	
	public User checkRegisterUser(String email) {
		User user=userRepo.findByEmail(email);
		System.out.println(user);
		if(user==null) {
			return null;
		}
		return user;
	}

	@Override
	public List<User> getByRole(UserRole role) {
		// TODO Auto-generated method stub
		return userRepo.findByRole(role.toString());
	}

	@Override
	public User promoteAdmin(int user_id) {
		// TODO Auto-generated method stub
		User user=userRepo.findById(user_id).orElse(null);
		if(user==null) {
			return null;
		}
		user.setRole(UserRole.admin);
		userRepo.save(user);
		return user;
	}
	
	@Override
	public User demoteUser(int user_id) {
		// TODO Auto-generated method stub
		User user=userRepo.findById(user_id).orElse(null);
		if(user==null) {
			return null;
		}
		user.setRole(UserRole.user);
		userRepo.save(user);
		return user;
	}

	@Override
	public void savePwd(int id, String pwd) {
		// TODO Auto-generated method stub
		User user=userRepo.findById(id).orElse(null);
		user.setPassword(pwd);
		userRepo.save(user);
	}

}
