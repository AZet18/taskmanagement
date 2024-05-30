package pl.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.taskmanagement.entity.Task;
import pl.taskmanagement.entity.UserTask;
import pl.taskmanagement.repository.UserTaskRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final TaskService taskService;

    public List<UserTask> getUserTasksByUserId(Long userId) {
        return userTaskRepository.findByUserId(userId);
    }

    public UserTask addUserTask(UserTask userTask) {
        return userTaskRepository.save(userTask);
    }

    public UserTask updateTask(Long id, UserTask userTask) {
        Optional<UserTask> optionalUserTask = userTaskRepository.findById(id);
        if (optionalUserTask.isPresent()) {
            UserTask usertTaskExisting = optionalUserTask.get();
            usertTaskExisting.setPriority(userTask.getPriority());
            return userTaskRepository.save(usertTaskExisting);
        } else {
            throw new RuntimeException("Task not found with id " + id);
        }
    }

}
