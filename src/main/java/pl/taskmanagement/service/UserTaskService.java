package pl.taskmanagement.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.taskmanagement.entity.Status;
import pl.taskmanagement.entity.Task;
import pl.taskmanagement.entity.User;
import pl.taskmanagement.entity.UserTask;
import pl.taskmanagement.repository.TaskRepository;
import pl.taskmanagement.repository.UserRepository;
import pl.taskmanagement.repository.UserTaskRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

//    public void addUserTask(Long userId, Long taskId, UserTask userTask) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
//
//        UserTask newUserTask = new UserTask();
//        newUserTask.setUser(user);
//        newUserTask.setTask(task);
//        newUserTask.setPriority(userTask.getPriority());
//        newUserTask.setStatus(userTask.getStatus());
//        newUserTask.setDeadline(userTask.getDeadline());
//        userTaskRepository.save(newUserTask);
//    }
//
//    public void deleteUserTask(Long userTaskId) {
//        userTaskRepository.deleteById(userTaskId);
//    }

    public void addUserTask(AddUserTaskParams params) {
        User user = userRepository.findById(params.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Task task = taskRepository.findById(params.getTaskId()).orElseThrow(() -> new RuntimeException("Task not found"));

        UserTask newUserTask = new UserTask();
        newUserTask.setUser(user);
        newUserTask.setTask(task);
        newUserTask.setPriority(params.getPriority());
        newUserTask.setStatus(params.getStatus());
        newUserTask.setDeadline(params.getDeadline());
        userTaskRepository.save(newUserTask);
    }

    @Transactional
    public void deleteUserTask(Long userTaskId, Long userId) {
        UserTask userTask = userTaskRepository.findById(userTaskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!userTask.getUser().getId().equals(userId)) {
            throw new RuntimeException("User not authorized to delete this task");
        }

        userTaskRepository.removeUserTask(userTask.getId());
    }

    public UserTask findById(Long userTaskId) {
        return userTaskRepository.findById(userTaskId)
                .orElseThrow(() -> new RuntimeException("User task not found with id: " + userTaskId));
    }

//    public void deleteUserTask(Long userTaskId) {
//        userTaskRepository.deleteById(userTaskId);
//    }


    public List<UserTask> getUserTasksByUserId(Long userId) {
        return userTaskRepository.findByUserId(userId);
    }

    public UserTask updateTask(Long id, UserTask userTask) {
        return userTaskRepository.findById(id)
                .map(existingUserTask -> {
                    existingUserTask.setPriority(userTask.getPriority());
                    existingUserTask.setDeadline(userTask.getDeadline());
                    existingUserTask.setStatus(userTask.getStatus());
                    existingUserTask.setTask(userTask.getTask());
                    return userTaskRepository.save(existingUserTask);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    @Value
    @Builder
    public static class AddUserTaskParams {
        private Long userId;
        private Long taskId;
        private int priority;
        private Status status;
        private LocalDate deadline;


    }

}
