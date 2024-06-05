package pl.taskmanagement.controller.model;

import lombok.Data;
import pl.taskmanagement.entity.Status;
import pl.taskmanagement.entity.UserTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
public class UserTaskViewModel {

    private Long id;
    private String taskName;
    private int priority;
    private String deadline;
    private String status;
    private String createdDate;
    private String updatedDate;

    public static UserTaskViewModel of(UserTask userTask) {
        UserTaskViewModel userTaskViewModel = new UserTaskViewModel();
        userTaskViewModel.setId(userTask.getId());
        userTaskViewModel.setTaskName(userTask.getTask().getName());
        userTaskViewModel.setPriority(userTask.getPriority());
        userTaskViewModel.setDeadline(localDateAsString(userTask.getDeadline()));
        userTaskViewModel.setStatus(getStatus(userTask.getStatus()));
        userTaskViewModel.setCreatedDate(localDateTimeAsString(userTask.getCreatedDate()));
        userTaskViewModel.setUpdatedDate(localDateTimeAsString(userTask.getUpdatedDate()));
        return userTaskViewModel;
    }

    private static String localDateAsString(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(date -> date.format(DateTimeFormatter.ISO_DATE))
                .orElse("Empty date");

    }

    private static String localDateTimeAsString(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .orElse("Empty date time");

    }

    private static String getStatus(Status status) {
        switch (status) {
            case TO_DO -> {
                return "TODO";
            }
            case IN_PROGRESS -> {
                return "In progress";
            }
            case DONE -> {
                return "Done";
            }
            default -> {
                return "unknown";
            }
        }

    }
}
