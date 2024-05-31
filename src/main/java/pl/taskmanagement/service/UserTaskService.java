package pl.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.taskmanagement.entity.Status;
import pl.taskmanagement.entity.Task;
import pl.taskmanagement.entity.User;
import pl.taskmanagement.entity.UserTask;
import pl.taskmanagement.repository.TaskRepository;
import pl.taskmanagement.repository.UserRepository;
import pl.taskmanagement.repository.UserTaskRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void addUserTask(Long userId, Long taskId, UserTask userTask) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        UserTask newUserTask = new UserTask();
        newUserTask.setUser(user);
        newUserTask.setTask(task);
        newUserTask.setPriority(userTask.getPriority());
        newUserTask.setStatus(userTask.getStatus());
        newUserTask.setDeadline(userTask.getDeadline());
        userTaskRepository.save(newUserTask);
    }

    public List<UserTask> getUserTasksByUserId(Long userId) {
        return userTaskRepository.findByUserId(userId);
    }

    public UserTask updateTask(Long id, UserTask userTask) {
        return userTaskRepository.findById(id)
                .map(existingUserTask -> {
                    existingUserTask.setPriority(userTask.getPriority());
                    return userTaskRepository.save(existingUserTask);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

}
