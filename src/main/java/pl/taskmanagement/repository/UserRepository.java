package pl.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.taskmanagement.entity.User;
import pl.taskmanagement.entity.UserTask;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmailAndPassword(String email, String password);

    User findUserByUserTasks(UserTask id);

}
