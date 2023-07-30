package jels.JapaneseELearningSystem.service;

import java.util.List;

import jels.JapaneseELearningSystem.entity.Course;
import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.entity.UserRole;
import jels.JapaneseELearningSystem.entity.RegisterRequest;

public interface UserService {

	public User getById(int id);
	public User update(User user);
	public boolean addCourse(Course course,User user);
	public List<Course> getcourList(int userId);
	
	//---
List<User> getAll();
	
	
	
	User create(User user);
	
	User register(RegisterRequest registerUser, String password);
		
	User updateName(int id, String name);
	
	boolean delete(int id);
	
	User updatePwd(int id, String newPwd);
	
	public User checkLoginUser(String email, String password);
	
	public User checkRegisterUser(String email);
	
	public List<User> getByRole(UserRole role);
	
	public User promoteAdmin(int user_id);
	
	public User demoteUser(int user_id);
	
	public void savePwd(int id, String pwd);
}
