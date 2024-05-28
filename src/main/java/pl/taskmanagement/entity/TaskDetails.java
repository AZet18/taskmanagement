package pl.taskmanagement.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task_details")
public class TaskDetails {

    @Id
    private Long id;

    @Min(1)
    @Max(5)
    private int priority;

    @FutureOrPresent(message = "Task deadline cannot be in the past")
    private LocalDateTime deadline;

    private String status;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
