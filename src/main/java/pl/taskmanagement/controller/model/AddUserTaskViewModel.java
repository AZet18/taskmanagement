package pl.taskmanagement.controller.model;

import lombok.Data;
import pl.taskmanagement.entity.Status;
import pl.taskmanagement.service.UserTaskService.AddUserTaskParams;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class AddUserTaskViewModel {

    private Long taskId;
    private Integer priority;
    private String deadline;
    private String status;
    private List<Long> tagsIds;

    public AddUserTaskParams asParams(Long userId) {
        return AddUserTaskParams.builder()
                .priority(priority)
                .taskId(taskId)
                .userId(userId)
                .status(Status.valueOf(status))
                .tagIds(tagsIds != null ? tagsIds : List.of())
                .deadline(LocalDate.parse(deadline, DateTimeFormatter.ISO_DATE))
                .build();

    }

}
