package pl.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.taskmanagement.entity.UserTask;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

    List<UserTask> findByUserId(Long userId);
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user_task WHERE id = :userTaskId")
    void removeUserTask(@Param("userTaskId")Long userTaskId);



}
