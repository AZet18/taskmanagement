package pl.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.taskmanagement.entity.TaskDetails;

public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Long> {
}
