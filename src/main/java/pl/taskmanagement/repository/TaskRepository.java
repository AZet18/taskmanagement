package pl.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.taskmanagement.entity.Task;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tasks t JOIN user_task u ON t.id = u.task_id WHERE t.id=:taskId")
    List<Task> findTaskByUserTaskId(@Param("taskId")Long taskId);

}
