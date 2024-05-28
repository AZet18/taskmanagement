package pl.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.taskmanagement.entity.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {


}
