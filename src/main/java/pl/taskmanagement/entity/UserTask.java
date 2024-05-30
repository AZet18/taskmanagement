package pl.taskmanagement.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_task")
public class UserTask {
//polaczenie kto wykonuje kiedy jakie zadanie, np. w srode umyje auto, okna,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private int priority;

//    @FutureOrPresent(message = "Task deadline cannot be in the past")
//    private LocalDateTime deadline;

//    private String status;
//    @Column(name = "created_date") //todo jak ogarne reszte
//    private LocalDateTime createdDate;
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "asignee_id")
    private User user;

}
