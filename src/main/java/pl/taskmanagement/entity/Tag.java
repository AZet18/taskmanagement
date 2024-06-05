package pl.taskmanagement.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tags")
@NoArgsConstructor

public class Tag {
//praca fizyczna etc
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //physical work, relaxation, mental work,
    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;
}
