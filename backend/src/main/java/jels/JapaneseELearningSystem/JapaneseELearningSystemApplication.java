package jels.JapaneseELearningSystem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import jels.JapaneseELearningSystem.controller.UserController;
import jels.JapaneseELearningSystem.entity.Level;
import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.entity.UserRole;
import jels.JapaneseELearningSystem.repository.UserRepo;
import jels.JapaneseELearningSystem.service.LevelService;
import jels.JapaneseELearningSystem.service.UserService;

@SpringBootApplication
public class JapaneseELearningSystemApplication implements CommandLineRunner{
	

	public static void main(String[] args) {
		
		
		SpringApplication.run(JapaneseELearningSystemApplication.class, args);
		
		
		
	}
	
	@Autowired
	LevelService levelService;
	
	@Autowired
	UserRepo userRepo;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlMode;




	@Override
	public void run(String... args) throws Exception {
		
		if (ddlMode.equals("create")) {
			User user = new User();
			user.setName("admin");
			user.setRole(UserRole.user.admin);
			user.setEmail("elearning.jels@gmail.com");
			user.setPassword("11112222");
			
			userRepo.save(user);
			

			
		System.out.println("creating level");
		
		levelService.create(new Level(1,"N1"));
		levelService.create(new Level(2,"N2"));
		levelService.create(new Level(3,"N3"));
		levelService.create(new Level(4,"N4"));
		levelService.create(new Level(5,"N5"));
		System.out.println("levels created");
	}
		
	}

}
