package jels.JapaneseELearningSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jels.JapaneseELearningSystem.entity.User;
import jels.JapaneseELearningSystem.entity.UserRole;


@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User,Integer>{
	
public User findByEmail(String email);
	
	public User findByName(String name);
	
	@Query(value = "select * from user where role=:role", nativeQuery = true)
	public List<User> findByRole(String role);
	
	//@Query(value="delete from user u join u.watched_video wv where wv.id = :videoId", nativeQuery = true)
	@Modifying 
	@Query(value="delete from watched_video wv where wv.video_id = :videoId", nativeQuery = true)
	public void deleteWatchedVideosByVideoId(@Param("videoId") int videoId);

}
